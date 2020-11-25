package practicas.practica2;

import java.util.Scanner;

public class Karatsuba {
	
	public static void aumentar_grado(int res[], int n){
		for(int i = res.length-n-1; i >= 0; i--){
			res[i+n] = res[i];
			res[i] = 0;
		}
	}
	
	public static void multiplicar_polinomios(int p[], int q[], int grado_p, int grado_q, int res[]){
		int m, suma_p[], suma_q[];
		int prod1[], prod2[];
		m = Math.min(grado_p/2, grado_q/2);
		
		// pendiente de hacer
		// pa = p[m] ... p[grado_p], pb = p[0] ... p[m-1], qa = q[m] ... q[grado_q], qb = q[0] ... q[m-1]
		// (pa * qa)x^(2m)
		// ((pa + pb) (qa+qb) -(pa*qa) (pb*qb))x^m
		// (pb * qb)
	}
	
	public static void main(String args[]){
		int p[], grado_p, m;
		int q[], grado_q,  n;
		int res[], max;
		
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
				max = grado_p+grado_q+1;
				res = new int[max];
				multiplicar_polinomios(p,q,grado_p,grado_q,res);
			};		
		}
	}
}
