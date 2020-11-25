# -*- coding: utf-8 -*-

#sumatorio de los n numeros naturales mayores o giuales que 0
def suma(n):
    if(n >= 0):
        if(n == 0):
            return 0;
        else:
            res = suma(n-1);
            res = res + 2*n -1;
            return res;
    else:
        return -1;
 # resultado es n*n  


#calculo del numero de digitos de un numero
def num_digitos(n):
     if(n < 0):
         n = -n;
     if(n<10):
         return 1;
     else:
         res = num_digitos(n//10);
         return res+1;


#version recursiva del algoritmo de horner
def horner(array,x):
    return horner_aux(array,x,len(array)-1);
    
def horner_aux(array,x,pos):
    if(pos == 0):
        return array[pos];
    else:
        res = horner_aux(array,x,pos-1);
        res = res*x + array[pos];
        return res;


#funcion extraña
def fun(n,f):
    if(n==1.2):
        return 1;
    elif(n >= 3):
        res = 1;
        i = 1;
        while(i <= n-2):
            res = res+f(i);
            i=i+1;
        return res;


#raiz cuadrada mediante newton raphson
def raphson(x,a):
    #no se hacerlo
    return "no se hacerlo"

#insertar elemento nuevo en una lista ordenado
def lista_ordenada(lista,elemento):
    lista = insertar(lista,elemento,0,len(lista));
    print(lista);

def insertar(lista,elemento,pos,final):
    if(pos==final):
        lista.append(elemento);
        return lista;
    else:
        if(elemento <= lista[pos]):
            lista.insert(pos,elemento);
            return lista;
        else :
            return insertar(lista,elemento,pos+1,final);
        

#implementacion recursiva de insert_sort
def insert_sort(lista):
    aux = []
    aux.append(lista[0])
    return insert_sort2(lista,aux,1,len(lista))
    
def insert_sort2(lista,aux,pos,fin):
    if(pos == fin):
        return aux
    lista_ordenada(aux,lista[pos])
    return insert_sort2(lista,aux,pos+1,fin)
    
    
#implementacion de la variacion de las torres de hanoi
def hanoi(n):    
    if(n>1):
        hanoi2(n-1,'X','Y')
        hanoi2(n-1,'Y','Z')
    
    paso1 = '1- Mueve disco '+ str(n) +' desde torre X a torre Y'
    print(paso1)
    
    if(n>1):
        hanoi2(n-1,'Z','Y')
        hanoi2(n-1,'Y','X')
    
    paso2 = '2- Mueve disco '+ str(n) +' desde torre Y a torre Z'
    print(paso2)
    
    if(n>1):
        hanoi(n-1)

def hanoi2(n,origen,destino): 
    
    if(origen == 'X'):
        destino2 = 'Z'
    
    elif(origen == 'Z'):
        destino2 = 'X'
    
    else:
        if(destino == 'X'):
            destino2 = 'Z'
        else:
            destino2 = 'X'
    
    if(n>1):
        if(origen == 'Y'):
            hanoi2(n-1,origen,destino2)
        else:
            hanoi2(n-1,origen,destino)
            hanoi2(n-1,destino,destino2)
        
    pasoN = 'N- Mueve disco '+ str(n) +' desde torre '+ origen+' a torre '+destino
    print(pasoN)
        
    if(n>1):
        if(origen == 'Y'):
            hanoi2(n-1,destino2,origen)
            hanoi2(n-1,origen,destino)
        else:
            hanoi2(n-1,destino2,destino)