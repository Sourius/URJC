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

def fill(lista,m,n):    
    while m < n:
        lista.append(0);
        m = m+1;
    return fill;

#formatear polinomios
def formatear_polinomios(lista, grado, res):
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
        return formatear_polinomios(lista, grado, res);

#sumar y restar polinomios
def suma(v1, v2, res, pos, n):
    if(pos == n):
        return suma;
    
    else:
        res.append(v1[pos] + v2[pos]);
        suma(v1,v2,res,pos+1,n);
    
def resta(v1, v2, res, pos, n):
    if(pos == n):
        return resta;
    
    else:
        res.append(v1[pos] - v2[pos]);
        resta(v1,v2,res,pos+1,n);
    
def sumar_y_restar_polinomios():
    grado_v1 = getLength();
    v1 = getList();
    n1 = grado_v1+1;
    
    grado_v2 = getLength();
    v2 = getList();
    n2 = grado_v2+1;
    
    if(checkList(n1,v1) and checkList(n2,v2)):
        m = min(n1,n2);
        n = max(n1,n2);
        
        if(n1 != n2):
            if(m == n1):
                fill(v1,m,n);
            else:
                fill(v2,m,n);
        
        res_suma = [];
        res_resta = [];
        
        suma(v1, v2, res_suma, 0, n);
        resta(v1, v2, res_resta, 0, n);
                
        res="";
        print(formatear_polinomios(res_suma, n-1, res));
        print(formatear_polinomios(res_resta, n-1, res));

def main():
    sumar_y_restar_polinomios();

if __name__ == "__main__":
    main();