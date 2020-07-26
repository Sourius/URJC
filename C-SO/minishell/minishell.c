//librerias
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <signal.h>
#include "parser.h"
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

//metodos principales
int main();  //programa principal
void executeCommand(tline* command);  		//mandatos en primer plano
void executeInBG(tline* command, char* cadena);	//mandatos en segundo plano

//redirecciones 
int redirect(char *name, int n); //redirigir entrada, salida y/o error	
void restoreRedirections();  	  //restaurar stdin,stdout,stderr

//mandatos internos
void executeCD(); 	 //cd
void executeJobs();  	 //jobs
void executeFG(int pos); //fg -> sin implementar

//manejador para procesos en segundo plano
void bgChildHandler(int sig);

//variables globales
typedef struct{ 
	//informacion de procesos en background
	pid_t pid;
	char* bCommand;
	int done;
}Back;

Back* ps_background; //lista de procesos en segundo plano
int pidsB;	  // numero de procesos en segundo plano
int pidsBMAX;	  //tamaño maximo de la lista

int std_in, std_out, std_err; //para restaurar stdin, stdout y stderr del sistema

//Shell
int main(){
	char read_line[1024];
	tline * line; 

	pidsB = 0; 
	pidsBMAX = 10; 
	//reserva de memoria para almacenar procesos en background
	ps_background=malloc(sizeof(Back)*pidsBMAX); 

	//manejadores para la shell
	signal(SIGINT, SIG_IGN);
	signal(SIGQUIT, SIG_IGN);
	
	//para restablecer stdin, stdout y stderr del sistema 		
	dup2(0,std_in);
	dup2(1,std_out);
	dup2(2,std_err);

	printf("\nmsh> "); // mostrar prompt

	while((fgets(read_line,1024,stdin)) && (strcmp(read_line,"exit\n") != 0)){
		fflush(stdin);
		
		//para evitar coredump si se introduce una linea vacia
		if(strcmp(read_line,"\n")!=0){ 
			line = tokenize(read_line);

			//si es en segundo plano
			if(line->background == 1){
				// aumentar espacio si es necesario
				if(pidsB == pidsBMAX){ 
					pidsBMAX = pidsBMAX*2;
					ps_background=realloc(ps_background,sizeof(Back)*pidsBMAX);
				}
				executeInBG(line,read_line); //ejecutar mandato
			}
			//si es en primer plano
			else{
				//mandatos internos
				if(line->commands->filename == NULL){

					if(strcmp(line->commands[0].argv[0],"jobs") == 0) executeJobs(); //jobs
		
					else if(strcmp(line->commands[0].argv[0],"cd")==0) //cd
						executeCD(line->commands->argv);
					
					else if(strcmp(line->commands[0].argv[0],"fg")==0){ //fg
						//ultimo mandato en background si no se especifica el numero
						if(line->commands->argc==1) executeFG(pidsB-1);
						//mandato segun el numero que acompaña al mandato fg 
						else executeFG(atoi(line->commands->argv[1])); 
					}
					
					//mandato no valido
					else {
						if(line->redirect_error != NULL) redirect(line->redirect_error,2);
						fprintf(stderr,"%s: No se encuentra el mandato.\n", line->commands[0].argv[0]);
						dup2(std_err,2);
					}
				}
			
				//ejecutar comandos externos
				else executeCommand(line);
			}
		}
		printf("\nmsh> "); //mostrar prompt
	}
	// liberar espacio
	free(ps_background); 
	return 0;
}

//Ejecución de mandatos en segundo plano
void executeInBG(tline* command, char* cadena){ //no esta terminado
	pid_t pid;
	int n;
	n=pidsB;
	pidsB++;
	
	pid=fork();
	if(pid < 0){
		fprintf(stderr,"Fallo en el fork: %s",strerror(errno));
		exit(1);
	}
	if(pid == 0){
		signal(SIGINT , SIG_IGN);
		signal(SIGQUIT, SIG_IGN);
		
		// quitarle la entrada y salida estandar al proceso
		// no implementado
			
		if(command->commands->filename != NULL){
			//ejecutar comando
			if(execvp(command->commands[0].argv[0],command->commands[0].argv) != 0){
				fprintf(stderr,"El comando %s no es válido.\n", command->commands[0].argv[0]);
				exit(1);
			}
		}
		exit(0);
	}
	else{
		// manejador para saber cuando terminan los procesos en segundo plano
		signal(SIGCHLD, bgChildHandler);

		printf("[%d]\t%d\n",n,pid);
		if(command->commands->filename != NULL){
			// guardar proceso en la lista
			ps_background[n].pid = pid;
			ps_background[n].bCommand = malloc(strlen(cadena));
			strcpy(ps_background[n].bCommand, cadena);
			ps_background[n].done = 0;	
		}
		else{
			pidsB--;
		}
	}
}

//Ejecución de mandatos en primer plano
void executeCommand(tline* line){
	pid_t pid;
	int pipes_fd[line->ncommands-1][2];
	int error, exit_code;
	
	error=0;
	exit_code = 0;
	
	for(int i = 0; i < line->ncommands-1; i++){ 
		//tuberias para enlazar los mandatos
		if(pipe(pipes_fd[i])==-1){ 
			//en caso de fallo, cerrar tubrias abiertas
			fprintf(stderr,"Error en el pipe: %s\n",strerror(errno));
			error=-1;
		}
	}
	
	//crear tantos hijos como mandatos para poder ejecutarlos
	for(int i = 0; i< line->ncommands && error!=-1; i++){
		pid = fork();
		if(pid < 0){ //si la creación de algún hijo
			fprintf(stderr,"Error en el fork: %s\n",strerror(errno));
			error = -1;
		}

		else if(pid == 0){ //Hijos
			//en este caso se necesita que las señales terminen por defecto 
			signal(SIGINT , SIG_DFL);
			signal(SIGQUIT, SIG_DFL);
			//primer comando
			if(i == 0){
				if(line->ncommands>1){
					//cerrar descritores que no se utilizan
					close(pipes_fd[i][0]); 
					//redireccion de salida a la primera tuberia
					dup2(pipes_fd[i][1],1); 
				}
				// redireccion de entrada estandar
				if(line->redirect_input != NULL) exit_code = redirect(line->redirect_input,0);	
			}
			
			// ultimo
			if(i == line->ncommands-1){
				if(line->ncommands > 1){ 
					//cerrar conexion que no se usa
					close(pipes_fd[i-1][1]);
					// redirección de entrada a la tuberia
					dup2(pipes_fd[i-1][0],0); 
				}
				// redireccion de stdout y stderr en el ultimo mandato
				
				if(line->redirect_output != NULL) exit_code = redirect(line->redirect_output,1);
				if(line->redirect_error != NULL) exit_code = redirect(line->redirect_error,2);
			}
			
			// mandatos de en medio
			else if(i > 0) {
				//se cierrran todos los extremos que no se usan
				close(pipes_fd[i][0]);	
				close(pipes_fd[i-1][1]);

				//se redirige la entrada y la salida	
				dup2(pipes_fd[i-1][0],0);	
				dup2(pipes_fd[i][1],1);
			}
			
			// si hay algun fallo
			if(exit_code < 0) {
				// cerrar tuberias
				for(int j = 0; j < line->ncommands-1; j++){ 
					close(pipes_fd[j][0]);
					close(pipes_fd[j][1]);
				}
				exit(1);
			}
			else {
							
				exit_code = execvp(line->commands[i].argv[0],line->commands[i].argv);
				exit(exit_code);
			}
		}
		else{
			waitpid(pid,NULL,0); // esperar a que el hijo termine

			// cerrar tuberias utilizadas
			if(i > 0) close(pipes_fd[i-1][0]);
			if(i!=line->ncommands-1)close(pipes_fd[i][1]);
			//se deja el ultimo descritor para stdout
		}
		
		if(error < 0){
			// cerrar tuberias abiertas en caso de error
			for(int j = i; j < line->ncommands-1; j++){ 
				if(j == i && j > 0) close(pipes_fd[j-1][0]);
				close(pipes_fd[j][0]);
				close(pipes_fd[j][1]);
			}
		}
	}
	restoreRedirections(); //se restaura la entrada, salida y error estandar
}

//Se redirige la salida, entrada o error dependiendo del valor de n:entrada (0), salida (1) y/o error (2) estandar
int redirect(char* name ,int n){
	FILE* f;
	int outfd,infd ,errfd;
	
	switch(n){
		case 0:
			// redireccion de entrada estandar
			if((f= fopen(name,"r"))==NULL){
				fprintf(stderr,"%s: Error. %s.\n",name,strerror(errno));
				return -1;
			}else{
				infd = fileno(f); 
				dup2(infd,fileno(stdin)); 
				close(infd); 
			}
			break;
		
		case 1:
			//redireccion de salida estandar
			if((outfd = open(name, O_WRONLY|O_CREAT|O_TRUNC, 0666))<0){ 
				fprintf(stderr,"%s: Error. %s.\n",name,strerror(errno));
				return -1;
			}
			else{
				dup2(outfd,fileno(stdout));
				close(outfd);
			}
			break;
		
		case 2:
			// redireccion de error estandar
			if((errfd = open(name, O_WRONLY|O_CREAT|O_TRUNC, 0666))<0){
       				fprintf(stderr,"%s: Error. %s.\n",name,strerror(errno));	
       				return -1;
       			}
       			else{
       				
       				dup2(errfd,fileno(stderr));
       				close(errfd);
       			}
			break;
	}	
	return 0;
}

void restoreRedirections(){ 
        //restaurar stdin, stdout, stderr
	dup2(std_in,0);
	dup2(std_out,1);
	dup2(std_err,2);
}


//Mandatos internos de la shell
// cd
void executeCD(char** argv){
	char aux[1024];
	char dir[1024];

	if(argv[1]==NULL){
		//cambiar a home si no se indica el directorio
		strcpy(dir,getenv("HOME"));
		if(dir == NULL){
			fprintf(stderr,"No se puede acceder al directorio home!\n%s",strerror(errno));
		}
		else{
			if(chdir(dir) != 0){
				fprintf(stderr,"No se puede acceder al directorio '%s'!\n%s",dir,strerror(errno));
			} else {
				getcwd(aux,1024);
				printf("%s\n",aux);
			}
		}
	}
	else{
		// cambiar a directorio indicado
		strcpy(dir,argv[1]);
		if(chdir(dir) != 0){
			fprintf(stderr,"No se puede acceder al directorio %s!\n%s",dir,strerror(errno));
		} else {
			getcwd(aux,1024);
			printf("%s\n",aux);
		}
	}
}

//jobs
void executeJobs(){
	//mostrar el estado de cada proceso en background
	for(int i = 0; i < pidsB; i++){ 
		if(ps_background[i].done==0){
			printf("[%d]\tRunning\t%s",i+1,ps_background[i].bCommand);
		}else{
			printf("[%d]\tDone\t%s",i+1,ps_background[i].bCommand);
		}
	}
}

//fg
void executeFG(int pos){
	printf("Fg %d no se puede realizar, no esta implementado.\n", pos);
	// devolver la entrada y salida estandar al proceso
	// no esta implementado
}

// manejador para los procesos en segundo plano
void bgChildHandler(int sig){
	int n = 0;
	pid = getpid();
	// comprovar si esta en segundo plano
	while(n < pidsB){ 
		if(ps_background[n].pid == pid && ps_background[n].done == 0){ 
			// marcar proceso hijo como finalizado
			ps_background[n].done=1;
			// mostrar resultado del proceso hijo
			// no implementado
			break;
		}
		n++;
	}
}
