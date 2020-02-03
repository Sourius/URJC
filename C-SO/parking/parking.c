#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <time.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#define TIME_ENTRADA_CAMION 5
#define TIME_ENTRADA_COCHE 5
#define TIME_PARKING_CAMION 5
#define TIME_PARKING_COCHE 5

int main ();
int obtener_valores();
int preparar_parking();
void* aparcar_coche();
void* aparcar_camion();
void printParking();

int n_coches=0;
int n_plazas=0;
int n_plantas=0;
int n_camiones=0;

typedef struct{
    int* plazas;
    int plazas_libres;
} Plantas;

Plantas* parking;
int capcidad_maxima;
int ocupacion;
pthread_mutex_t mutex;
pthread_cond_t libre;
pthread_cond_t dos_libres;

int main (int argc, char* argv[]){
    int i;
    int j;
    pthread_t* threads_coches;
    pthread_t* threads_camiones;
    int* id_coches;
    int* id_camiones;
    
    ocupacion=0;
    if(obtener_valores(argc,argv)!=0){
    	fprintf(stderr, "Numero de parametros incorrecto: %s.\n",strerror(errno));
    	exit(1);
    }
    pthread_mutex_init(&mutex,NULL);
    pthread_cond_init(&libre, NULL);
    pthread_cond_init(&dos_libres,NULL);
    capcidad_maxima=n_plantas*n_plazas;
    id_coches=malloc(n_coches * sizeof(int));
    id_camiones=malloc(n_camiones * sizeof(int));
    threads_coches=malloc(n_coches * sizeof(pthread_t));
    threads_camiones=malloc(n_camiones * sizeof(pthread_t));
    parking=malloc(n_plantas * sizeof(Plantas));
    preparar_parking(n_plantas,n_plazas);
    
    printf("SE HAN CREADO %d PLANTAS CON %d PLAZAS. %d COCHES Y %d CAMIONES\n",n_plantas,n_plazas,n_coches,n_camiones);
    for(i=1; i<=n_coches; i++){
        id_coches[i-1]=i;
        pthread_create(&threads_coches[i],NULL,aparcar_coche,(void*)&id_coches[i-1]);
    }
    for(j=101; j<=n_camiones+100; j++){
        id_camiones[j-101]=j;
        pthread_create(&threads_camiones[j],NULL, aparcar_camion,(void*)&id_camiones[j-101]);
    }
    while(1);
}

void* aparcar_coche(void *id){
    int coche_id = *(int *)id;
    int i;
    int j;
    while(1){
        sleep(rand() % (TIME_ENTRADA_COCHE+1));
        pthread_mutex_lock(&mutex);
        while(ocupacion == capcidad_maxima){
            printf("ESPERA: Coche %d esperando... (ocupacion %d / %d)\n", coche_id, ocupacion, capcidad_maxima);
            pthread_cond_wait(&libre,&mutex);
        }

        for(i=0;i<n_plantas;i++){
            if(parking[i].plazas_libres>0){
                for(j=0;j<n_plazas;j++){
                    if(parking[i].plazas[j]==0){
                        parking[i].plazas[j]=coche_id;
                        parking[i].plazas_libres--;
                        ocupacion++;
                        printf("ENTRADA: Coche %d aparcado en la plaza %d de la planta %d. Ocupacion: %d.\n",coche_id,j,i,ocupacion);
                        printParking();
                        printf("\n");
                        pthread_mutex_unlock(&mutex);

                        sleep(rand() % (TIME_PARKING_COCHE+1));

                        pthread_mutex_lock(&mutex);
                        ocupacion--;
                        parking[i].plazas[j]=0;
                        parking[i].plazas_libres++;
                        printf("SALIDA: Coche %d deja libre la plaza %d de la planta %d. Ocupacion: %d.\n",coche_id,j,i,ocupacion);
                        printParking();
                        printf("\n");

                        if(parking[i].plazas_libres>1){
                            if (j==n_plazas-1){
                                if(parking[i].plazas[j-1]==0) pthread_cond_signal(&dos_libres);
                                else pthread_cond_signal(&libre);
                            }
                            else{
                                if(parking[i].plazas[j+1]==0) pthread_cond_signal(&dos_libres);
                                else pthread_cond_signal(&libre);
                            }
                        }
                        else pthread_cond_signal(&libre);

                        break;
                    }
                }
                break;
            } 
        }
        pthread_mutex_unlock(&mutex);
    }
}

void* aparcar_camion(void *id){
    int camion_id = *(int *)id;
    int i;
    int j;
    int aleatorio;

    while(1){
        sleep(rand() % (TIME_ENTRADA_CAMION+1));
        pthread_mutex_lock(&mutex);
        while(ocupacion == capcidad_maxima){
            printf("ESPERA: Camion %d esperando... (ocupacion %d / %d)\n", camion_id, ocupacion, capcidad_maxima);
            pthread_cond_wait(&dos_libres,&mutex);
        }

        for(i=0;i<n_plantas;i++){
            if(parking[i].plazas_libres>1){
                for(j=0;j<n_plazas-1;j++){
                    if((parking[i].plazas[j]==0)&&(parking[i].plazas[j+1]==0)){
                        parking[i].plazas[j]=camion_id;
                        parking[i].plazas[j+1]=camion_id;
                        ocupacion+=2;
                        parking[i].plazas_libres-=2;
                        printf("ENTRADA: Camion %d aparcado en la plaza %d y %d de la planta %d. Ocupacion: %d.\n",camion_id,j,j+1,i,ocupacion);
                        printParking();
                        printf("\n");
                        pthread_mutex_unlock(&mutex);

                        sleep(rand() % (TIME_PARKING_CAMION+1));
                        pthread_mutex_lock(&mutex);
                        ocupacion-=2;
                        parking[i].plazas[j]=0;
                        parking[i].plazas[j+1]=0;
                        parking[i].plazas_libres+=2;
                        printf("SALIDA: Camion %d deja libre la plaza %d y %d de la planta %d. Ocupacion: %d.\n",camion_id,j,j+1,i,ocupacion);
                        printParking();
                        printf("\n");

                        aleatorio=(int)(rand() % 10);
                        if(aleatorio%2==0) pthread_cond_signal(&dos_libres);
                        else {
                            pthread_cond_signal(&libre);
                            pthread_cond_signal(&libre);
                        }
                        break;
                    }
                }
                break;
            } 
        }
        pthread_mutex_unlock(&mutex);
    }
}

int obtener_valores(int n, char*valores[]){
    if(n < 3 || n > 5) return 1;
   	else{
	   	n_plazas = atoi(valores[1]);
	   	n_plantas = atoi(valores[2]);
	   	if(n == 3) n_coches = 2*n_plantas*n_plazas;
	   	else {
	   		n_coches = atoi(valores[3]);
	   		if(n == 5) n_camiones = atoi(valores[4]);
	   	}
   	}
   	return 0;
}

int preparar_parking(){
    int i;
    int j;
    for(i=0; i<n_plantas; i++){
        parking[i].plazas=malloc(n_plazas * sizeof(int));
        parking[i].plazas_libres=n_plazas;
        for(j=0; j<n_plazas; j++){
            parking[i].plazas[j]=0;
        }
    }
    return 0;
}

void printParking(){
    int i;
    int j;
    printf("Parking:\n");
    for (i=0; i<n_plantas; i++){
        printf("\tPlanta %d: ",i);
        for(j=0; j<n_plazas; j++){
            printf("[%d]\t", parking[i].plazas[j]);
        }
        printf("\n");
    }
}