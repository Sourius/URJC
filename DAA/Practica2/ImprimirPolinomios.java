package practicas.practica2;

import java.util.Scanner;

public class ImprimirPolinomios {
	static boolean checkList(String aux[], int n){
		return aux.length == n;
	}
	
	static int[] getList(String aux, int n){
		String list[] = aux.split(" ");
		int v[] = null;
		if(checkList(list,n)){
			v = new int [n];
			int i = 0;
			for(String s: list){
				v[i] = Integer.parseInt(s);
				i++;
			}
		}
		return v;
	}
	
	public static void imprimir_polinomio(int v[], int grado){
		int aux;
		aux = v[grado];
		String text;
		
		if(aux < 0) {
			text = " - ";
			aux = Math.abs(aux);
		}
		else text = " + ";
		
		if(grado == 0){
			System.out.print(text+aux);
			System.out.println();
		}else{
			if(aux != 0) System.out.print(text+aux+"x^"+grado);	
			imprimir_polinomio(v,grado-1);
		}
	}
	
	public static void main(String args[]){
		int grado, n, v[];
		Scanner sc = new Scanner(System.in);
		
		grado = sc.nextInt();
		sc.nextLine();
		String elementos = sc.nextLine();
		sc.close();
		
		v = getList(elementos, grado+1);
		if(v != null){
			imprimir_polinomio(v, grado);
		}
	}
}
