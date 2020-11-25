package practicas.practica1;

import java.util.Scanner;

public class ListaOrdenada {
	static int[] getList(String aux){
		String list[] = aux.split(" ");
		int v[] = new int [list.length];
		int i = 0;
		for(String s: list){
			v[i] = Integer.parseInt(s);
			i++;
		}
		return v;
	}
	
	static void printSolution(int sol[], int n){
		for(int i = 0; i < n; i++){
			System.out.print(sol[i]);
			if(i != n-1) System.out.print(" ");
		}
		System.out.println();
	}
	
	static int[] insertar(int n, int v[], int elemento){
		boolean hecho = false;
		int m = n+1;
		int aux[] = new int[m];
				
		for(int i = 0; i < m; i++){
			if(i < n && v[i] <= elemento) aux[i] = v[i];
			else {
				if (!hecho){
					aux[i] = elemento;
					hecho = true;
				}
				else aux[i] = v[i-1];
			}
		}
		return aux;
	}
	
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		int n, elemento;
		
		n = sc.nextInt();
		sc.nextLine();
		String elementos = sc.nextLine();
		elemento = sc.nextInt();	
		sc.close();
		
		int v[];
		v = getList(elementos);
		v = insertar(n,v,elemento);
		printSolution(v,n+1);
	}
}
