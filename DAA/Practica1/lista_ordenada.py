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

def lista_ordenada():
    n = getLength();
    lista = getList();
    
    if(checkList(n,lista)):
        elemento = int(input());
        insertar(lista,elemento,0,n);
        return lista;
    

def main():
    res = getResult(lista_ordenada());
    print(res);

if __name__ == "__main__":
    main();