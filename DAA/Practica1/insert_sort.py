# -*- coding: utf-8 -*-
def getList():
    lista= input();
    lista = lista.split();
    aux = []
    
    for x in lista:
        aux.append(int(x));
    return aux;

def checkList(n,lista):
    return (n == len(lista));

def getLength():
    n = int(input());
    return n;

def getResult(lista):
    res='';
    for x in lista:
        res = res+' '+str(x);
    
    res = res.lstrip();
    return res;

def insertar(lista,elemento,pos,final):
    if(pos == final):
        lista.append(elemento);
    
    else:
        if(elemento <= lista[pos]):
            lista.insert(pos,elemento);
        
        else :
            insertar(lista,elemento,pos+1,final);

def lista_ordenada(n,lista,elemento):
    insertar(lista,elemento,0,n);
        
def insert_sort2(lista,aux,pos,fin):
    if(pos == fin):
        return aux;
    
    else:
        lista_ordenada(len(aux),aux,lista[pos])
        insert_sort2(lista,aux,pos+1,fin);


def insert_sort():
    n = getLength();
    lista = getList();
    if(checkList(n,lista)):
        aux=[];
        insert_sort2(lista,aux,0,n);  
        return aux;


def main():
    res = getResult(insert_sort());
    print(res);

if __name__ == "__main__":
    main();