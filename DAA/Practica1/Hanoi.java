package practicas.practica1;

import java.util.Scanner;

public class Hanoi {
	
	static void hanoi(int n, int origen, int destino){
				int nuevoDestino;
		if(n == 1){
			if((origen == 1 || origen == 3) && destino != 2){
				nuevoDestino = 2;
				System.out.println("Mueve disco "+n+" desde la torre "+origen+" a torre "+nuevoDestino);
				System.out.println("Mueve disco "+n+" desde la torre "+nuevoDestino+" a torre "+destino);
			}
			else System.out.println("Mueve disco "+n+" desde la torre "+origen+" a torre "+destino);
		}
		else{
			if(origen == 1 || origen == 3){
				if(destino == 2) {
					if(origen == 1) nuevoDestino = 3;
					else nuevoDestino = 1;
					hanoi(n-1,origen,destino);	
					hanoi(n-1,origen,nuevoDestino);
					System.out.println("Mueve disco "+n+" desde la torre "+origen+" a torre "+destino);
					hanoi(n-1,nuevoDestino,destino);
				}
				else{
					nuevoDestino = 2;
					hanoi(n-1,origen,nuevoDestino);
					hanoi(n-1,nuevoDestino,destino);
					System.out.println("Mueve disco "+n+" desde la torre "+origen+" a torre "+nuevoDestino);
					hanoi(n-1,origen,nuevoDestino);
					hanoi(n-1,nuevoDestino,origen);
					System.out.println("Mueve disco "+n+" desde la torre "+nuevoDestino+" a torre "+destino);
					hanoi(n-1,origen,nuevoDestino);
					hanoi(n-1,nuevoDestino,destino);
				}
				
			}
			// origen = 2
			else{
				if(destino == 1) nuevoDestino = 3;
				else nuevoDestino = 1;
				hanoi(n-1,origen,nuevoDestino);
				System.out.println("Mueve disco "+n+" desde la torre "+origen+" a torre "+destino);
				hanoi(n-1,nuevoDestino,origen);
				hanoi(n-1,origen,destino);
			}
		}
	}
	
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		sc.close();
		if(n <= 9) hanoi(n,1,3);
	}
}
