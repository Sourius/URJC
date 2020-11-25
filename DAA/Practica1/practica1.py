# -*- coding: utf-8 -*-
#metodos auxiliares
def getList():
    lista = input("Elementos de la lista sperados por espacios: ");
    lista = lista.split();
    
    aux = [];
    for x in lista:
        aux.append(int(x));
    return aux;
    

def checkList(n,lista):
    return (n == len(lista));

def getLength():
    n= int(input("Número de elementos de la lista: ")); 
    return n;

def getResult(lista):
    res='';
    for x in lista:
        res = res+' '+str(x);
    
    res = res.lstrip();
    return res;


#ejercicios
#raiz cuadrada mediante newton raphson
def raphson(x,a):
    #no se hacerlo
    return;

#insertar elemento nuevo en una lista ordenado
def lista_ordenada():
    n = getLength();
    lista = getList();
    
    if(checkList(n,lista)):
        elemento = int(input("Elemento a añadir: "));
        insertar(lista,elemento,0,n);
        print(getResult(lista));
    else:
        print("Error! el número de elementos de la lista no coincide");
        

def insertar(lista,elemento,pos,final):
    if(pos == final):
        lista.append(elemento);
    
    else:
        if(elemento <= lista[pos]):
            lista.insert(pos,elemento);
        
        else :
            insertar(lista,elemento,pos+1,final);
        

#implementacion recursiva de insert_sort
def lista_ordenada_2(n,lista,elemento):
    insertar(lista,elemento,0,n);       

def insert_sort2(lista,aux,pos,fin):
    if(pos == fin):
        return aux;
    
    else:
        lista_ordenada_2(len(aux),aux,lista[pos])
        insert_sort2(lista,aux,pos+1,fin);


def insert_sort():
    n = getLength();
    lista = getList();
    
    if(checkList(n,lista)):
        aux=[];
        insert_sort2(lista,aux,0,n);  
        res = getResult(aux);
        print(res);
        
    else:
        print("Error! el número de elementos de la lista no coincide");
    
#implementacion de la variacion de las torres de hanoi
def hanoi():  
    n = int(input("Número de discos: "));
    if(n < 0 or n > 9):
        print("Error! el número de discos debe ser un entero <= 9");
        
    else:
        if(n>1):
            hanoi2(n-1,'1','2');
            hanoi2(n-1,'2','3');
        
        paso1 = 'Mueve disco '+ str(n) +' desde torre 1 a torre 2'
        print(paso1)
        
        if(n>1):
            hanoi2(n-1,'3','2');
            hanoi2(n-1,'2','1');
        
        paso2 = 'Mueve disco '+ str(n) +' desde torre 2 a torre 3'
        print(paso2)
        
        if(n>1):
            hanoi2(n-1,'1','2');
            hanoi2(n-1,'2','3');

def hanoi2(n,origen,destino): 
    if(origen == '1'):
        destino2 = '3'
    
    elif(origen == '3'):
        destino2 = '1'
    
    else:
        if(destino == '1'):
            destino2 = '3'
        else:
            destino2 = '1'
    
    if(n>1):
        if(origen == '2'):
            hanoi2(n-1,origen,destino2)
        else:
            hanoi2(n-1,origen,destino)
            hanoi2(n-1,destino,destino2)
        
    pasoN = 'Mueve disco '+ str(n) +' desde torre '+ origen+' a torre '+destino
    print(pasoN)
        
    if(n>1):
        if(origen == '2'):
            hanoi2(n-1,destino2,origen)
            hanoi2(n-1,origen,destino)
        else:
            hanoi2(n-1,destino2,destino)