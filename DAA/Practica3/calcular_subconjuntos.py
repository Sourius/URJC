# -*- coding: utf-8 -*-
def getList():
    lista= input();
    lista = lista.split();
    aux = []
    
    for x in lista:
        aux.append(int(x));
    return aux;

def checkList(n,lista):
    return ((n == len(lista)) and (n <= 100));

def getLength():
    n = int(input());
    return n;

def isSolution(res,m):
    aux = len(res);
    if(aux == 1):
        return True;
    if(aux > m):
        return False;
    # el minimo debe ser el divisor del ultimo elemento añadido
    return (res[-1] % res[0] == 0);

# sub conjuntos donde el minimo es dividor de los m-1
def calcular_cuantas(lista, res, sub, pos, m, n):
    # si hemos recorriedo todos los elementos
    if(pos == n):
        return;
    
    # sin añadir el elemento actual
    calcular_cuantas(lista, res, sub, pos + 1, m, n);  
    
    #añadiendo el elemento actual
    sub.append(lista[pos]);
    if(isSolution(sub, m)):
        # si se encuentra algún subconjunto se añade a la lista de subconjuntos
        if(len(sub) == m): 
            res.append(sub);
            sub.clear();
        else:
            calcular_cuantas(lista, res, sub, pos + 1, m, n);
    return;
    
def get_sub_coleccion():
    n = getLength();
    lista = getList();
    
    if ((lista != []) and (checkList(n, lista))):
        m = getLength();
        if(m == 1):
            print(str(n));
        
        #los datos son correctos
        elif(m > 1 and m <= n):  
            res = [];
            sub = [];
            pos = 0;  
            # ordenar la lista para no tener que calcular el minimo todo el rato
            lista.sort();
            # obtener los subconjuntos con los elementos de la lista que satisfacen las condiciones
            calcular_cuantas(lista, res, sub, pos, m, n);
            print(len(res));
    # los datos no son correctos   

def main():
    get_sub_coleccion();

if __name__ == "__main__":
    main();