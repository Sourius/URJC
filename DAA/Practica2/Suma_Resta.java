package practicas.practica2;

import java.util.Scanner;

public class Suma_Resta {
	
	public static void fill(int res[], int a[], int i, int n ){
		for(int j = i; j < n; j++){
			res[i] = a[i];
		}
	}
	
	public static int[] sumar(int a[],int m, int b[], int n){
		int res[], max, min, i; 
		max = Math.max(m, n);
		min = Math.min(m, n);
		
		res = new int[max];
		i = 0;
		while(i < min){
			res[i] = a[i] + b[i];
			i++;
		}
		
		if(i < m) fill(res,a,i,max);
		else fill(res,b,i,max);
		return res;
	}
	
	public static int[] restar(int a[], int m, int b[], int n){
		int res[], max, min, i; 
		max = Math.max(m, n);
		min = Math.min(m, n);
		
		res = new int[max];
		i = 0;
		while(i < min){
			res[i] = a[i] - b[i];
			i++;
		}
		
		if(i < m) fill(res,a,min,max);
		else fill(res,b,min,max);
		return res;
	}
	
	public static void main(String args[]){
		int grado_p, grado_q, p[], q[];
		int suma[], resta[], m, n;
		int max;
		String aux_p, aux_q;
		Scanner sc = new Scanner(System.in);
		
		grado_p = sc.nextInt();
		sc.nextLine();
		aux_p = sc.nextLine();
		
		m = grado_p+1;
		p = ImprimirPolinomios.getList(aux_p, m);
		
		if(p != null){
			grado_q = sc.nextInt();
			sc.nextLine();
			aux_q = sc.nextLine();
			sc.close();
			
			n = grado_q+1;
			q = ImprimirPolinomios.getList(aux_q, n);
			
			if(q != null){
				suma = sumar(p,m,q,n);
				resta = restar(p,m,q,n);
				max = Math.max(grado_p, grado_q);
				ImprimirPolinomios.imprimir_polinomio(suma, max);
				ImprimirPolinomios.imprimir_polinomio(resta, max);
			}
		}
	}
}
