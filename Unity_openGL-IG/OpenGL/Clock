#include <GL/glut.h>
#include <GL/glu.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>
#include <unistd.h>

#ifdef WIN32
#include <windows.h>
#endif

#define PI 3.1415926535897932384626433832795

void print_number(char* s);
void reshape();
void display();
void init();
void advanceTime(int);
void updateTime();
void printDigitalTime();

//Radio del circulo
static int radius = 20;
time_t rawTime;
struct tm * currentTime;
char horaTotal[100];
int hora, minuto, segundo;
bool started = false;

// Funcion para pintar numeros
void print_number(char* s){
     if (s && strlen(s)) {
        while (*s) {
              glutBitmapCharacter(GLUT_BITMAP_9_BY_15, *s);
              s++;
        }
     }    
}

void reshape(int w, int h){
	glViewport(0, 0, (GLsizei) w, (GLsizei) h);	
}
void display(){
    glClear(GL_COLOR_BUFFER_BIT);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glLineWidth(1.0);
    
    // projección ortogonal de 20 unidades en todos los ejes
    glOrtho(-10.0,10.0,-10.0,10.0,10.0,-10.0);
    glColor3f(1.0f,1.0f,1.0f);
    
    //crear el reloj
    glPushMatrix();
    glScalef(0.45,0.45,0.45);
    glBegin(GL_LINE_LOOP);
    for(double i = 0; i < 2 * PI; i += PI / 24){
           glVertex3f(cos(i)*radius, sin(i)*radius, 0.0);  
    } 
    glEnd();
    glPopMatrix();
    
    char buffer[33];
    float radianesNumeros;
    
    // mostrar los numeros
    glPushMatrix();
    glScalef(0.45,0.45,0.45);
    for(int i = 0; i < 12; i++){
        radianesNumeros = (i+1)  * 30 * PI / 180.0;
        glRasterPos2f(sin(radianesNumeros)*21.4,cos(radianesNumeros)*21);
        itoa(i+1, buffer, 10);
        print_number(buffer);        
    }
    glPopMatrix();
    
    //crear las agujas del reloj
    //aguja para los segundos
    glPushMatrix();
    glScalef(0.45,0.45,0.45);
    glColor3f(0.0,1.0,0.0);
    glLineWidth(5.0);
    glBegin(GL_LINES);
    
    float thetas = 6.0 * PI * segundo / 180.0;
    glVertex3f(0.0,0.0,0.0);
    glVertex3f(17 * sin(thetas), 17 * cos(thetas),0.0);
    glEnd();
    glPopMatrix();
    
    //aguja para los minutos
    glPushMatrix();
    glScalef(0.45,0.45,0.45);
    glColor3f(0.0,0.0,1.0);
    glLineWidth(6.0);
    glBegin(GL_LINES);
    float thetam = minuto * PI * 6 / 180.0;
    glVertex3f(0.0,0.0,0.0);
    glVertex3f(15 * sin(thetam), 17 * cos(thetam),0.0);
    glEnd();
    glPopMatrix();
    
    //aguja para la hora 
    glPushMatrix();
    glScalef(0.45,0.45,0.45);
    glColor3f(1.0,0.0,0.0);
    glLineWidth(7.0);
    glBegin(GL_LINES);
    float thetah = hora * PI * 30 / 180.0;
    glVertex3f(0.0,0.0,0.0);
    glVertex3f(10* sin(thetah), 10 * cos(thetah),0.0);
    glEnd();
    glPopMatrix();    
    
    printDigitalTime();
    glFlush();
    
    // mover las agujas
    glutPostRedisplay();
}

void init(){
     glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
     //obtiene el tiempo local
     rawTime = time(NULL);
     currentTime = localtime(&rawTime);
     // obtiene la hora actual
     strftime(horaTotal, 100, "%I", currentTime);
     hora = atoi(horaTotal);
     // obtiene los minutos
     strftime(horaTotal, 100, "%M", currentTime);
     minuto = atoi(horaTotal);
     // obtiene los segundos
     strftime(horaTotal, 100, "%S", currentTime);
     segundo = atoi(horaTotal);   
}

void printDigitalTime(){
    glPushMatrix();
    glLineWidth(1.0);
    
    char text[20];
    int i;
    
    i=0;
    itoa(hora,text,10);
    // mostrar hora en (-3,-4) color rojo
    glColor3f(1.0,0.0,0.0);
    glRasterPos3f(-3,-4,1.0);
    while (i < strlen(text)) {
        glutBitmapCharacter(GLUT_BITMAP_HELVETICA_18, text[i]);
        i++;
    }
    
    i = 0;
    itoa(minuto,text,10);
    // mostrar minutos en (0,-4) color azul
    glColor3f(0.0,0.0,1.0);
    
    glRasterPos3f(0,-4,1.0);
    while (i < strlen(text)) {
        glutBitmapCharacter(GLUT_BITMAP_HELVETICA_18, text[i]);
        i++;
    }
    
    i=0;
    itoa(segundo,text,10);
    // mostrar segundos en (3,4) color verde
    glColor3f(0.0,1.0,0.0);
    glRasterPos3f(3,-4,1.0);
    while (i < strlen(text)) {
        glutBitmapCharacter(GLUT_BITMAP_HELVETICA_18, text[i]);
        i++;
    }
    glPopMatrix();
    glFlush();
}

// necesario para poder modificar el tiempo por teclas
void updateTime(){
    if (segundo >= 60)minuto += 1;
    if (minuto >= 60)hora += 1;
    segundo = segundo % 60;
    minuto = minuto % 60;
    hora = hora % 12;
    if (hora == 0)hora=12;
}

void advanceTime(int){
    segundo += 1;
    updateTime();
    glutTimerFunc(1000,advanceTime,0);
}

void keyPressed(unsigned char key, int a, int b) {
   //  printf("%c %d",key,key);
     switch (key) {
            case 's':
                 segundo += 1;
                 break;
            case 'm':
                minuto += 1;
                break;
            case 'h':
                hora += 1;
                break;
            case 'q':
                 exit(0);
                 break;
     }
     glutPostRedisplay();
}

int main(int argc, char **argv){
     glutInit(&argc, argv);
     glutInitDisplayMode(GLUT_RGB | GLUT_SINGLE);
     glutInitWindowSize(500, 500);
     
     glutInitWindowPosition(glutGet(GLUT_SCREEN_WIDTH)/6,glutGet(GLUT_SCREEN_HEIGHT)/6);

     glutCreateWindow("Reloj Analogico y Digital");
	 init();
     glutDisplayFunc(display);
     
     glutTimerFunc(1000,advanceTime,0);
     
     glutReshapeFunc(reshape);
     glutKeyboardFunc(keyPressed);
     
     glutMainLoop();
     return 0;
}
