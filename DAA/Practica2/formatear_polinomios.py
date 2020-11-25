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

def isNegative(n):
    return n<0;

# formatear polinomios
def formatear_polinomios(lista,grado, res):
    element = lista[grado];
    
    if(element != 0):
        if(isNegative(element)):
            sign = " - ";
        else:
            sign = " + ";
        res = res + sign + str(abs(element));
        
    if(grado == 0):
        if(res == ''):
            res=' + 0';
        return res;
    
    else:
        if(element != 0):
            res = res + "x^"+str(grado);  
        grado = grado - 1;
        return formatear_polinomios(lista,grado, res);

def polinomios():
    grado = getLength();
    lista = getList();
    
    n = grado+1;
    res = '';
    
    if(checkList(n,lista)):
        res = formatear_polinomios(lista,grado,res);
        print(res);
        
def main():
    polinomios();

if __name__ == "__main__":
    main();