# -*- coding: utf-8 -*-

def hanoi():  
    n = int(input());
    if(n < 0 or n > 9):
        return;
        
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
            
def main():
    hanoi();
    
if __name__ == "__main__":
    main();