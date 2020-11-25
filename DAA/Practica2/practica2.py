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

def fill_with_Zeros(lista,n):
    i = 0;
    while i < n:
        lista.insert(0,0);
        i = i+1;
    return lista;

def resize(x,y):
    n1 = len(x);
    n2 = len(y);
    dif = abs(n1-n2);
    
    if(dif == 0):
        return resize;
    else:
        if(n1 < n2):
            fill(x,n1,n2);
        else:
            fill(y,n2,n1);

def subList(lista,start,end):
    res = [];
    i = int(start);
    while (i < end):
        res.append(lista[i]);
        i = i+1;
    return res;

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
    else:
        print("Error! el tamaño de la lista no coincide.");
        
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

#karatsuba
def multiplicar(listaA,listaB):
    aux=[]
    
    #caso base: una de las listas esta vacia
    if(listaA == [] or listaB == []):
        return [];
    
    #caso base: una de las listas tiene un unico elemento
    elif(len(listaA) == 1):
        for x in listaB:
            aux.append(listaA[0]*x);
        return aux;
        
    elif(len(listaB) == 1):
        for x in listaB:
            aux.append(listaB[0]*x);
        return aux;
        
    else:
        nA = len(listaA);
        nB = len(listaB);       
        m = (min(nA,nB) // 2);
        
        #dividir los polinomios
        p_a = subList(listaA,m,nA);
        p_b = subList(listaA,0,m);
        q_a = subList(listaB,m,nB);
        q_b = subList(listaB,0,m);
       
        # res3 = (pb*qb)
        prod3 = multiplicar(p_b,q_b);
        res3 = prod3[0:len(p_b)]; 
        
        # res1 = (pa*qa)x^2m
        prod1 = multiplicar(p_a,q_a);
        res1 = fill_with_Zeros(prod1[0:len(prod1)],2*m);
        
        # suma1 = pa+pb
        suma1 = []; 
        resize(p_a,p_b);
        suma(p_a,p_b,suma1,0,len(p_a));
        
        # suma2 = qa+qb
        suma2 = []; 
        resize(q_a,q_b);
        suma(q_a,q_b,suma2,0,len(q_a));
        
        # prod2 = suma1 * suma2
        prod2 = multiplicar(suma1,suma2);
        
        # resta1 = prod2 - prod1
        resta1 = [];
        resize(prod1,prod2);
        resta(prod2,prod1,resta1,0,len(prod2));
        
        # resta2 = (resta1 - prod3) x^m
        resta2 = [];
        resize(resta1,prod3);
        resta(resta1,prod3,resta2,0,len(resta1));
        res2 = fill_with_Zeros(resta2[0:len(resta2)],m);
                
        # res = res1 + res2
        res = [];
        resize(res1,res2);
        suma(res1,res2,res,0,len(res1));
        
        # aux = res + res3
        resize(res,res3);
        suma(res,res3,aux,0,len(res));
        
    #aux = (pa*qa) x^2m + ((pa+pb)*(qa+qb)-(pa*qa) -(pb*qb)) x^m + pb*qb
    return aux;
        
def karatsuba():
    grado_p = getLength();
    v_p = getList();
    n_p = grado_p + 1;
    
    grado_q = getLength();
    v_q = getList();
    n_q = grado_q + 1;
    
    if(checkList(n_p,v_p) and checkList(n_q,v_q)):
        grado_max = grado_p + grado_q;
        res = "";
        lista = multiplicar(v_p,v_q);
        print(formatear_polinomios(lista,grado_max,res));