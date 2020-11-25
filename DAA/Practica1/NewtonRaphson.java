package practicas.practica1;

import java.util.Scanner;

public class NewtonRaphson {
	
	static double f(double x, int a){
		return Math.pow(x, 2) - a;
	}
	
	static double derivada_f(double x){
		return 2*x;
	}
	
	static double raizcuadrada(int x, int a){
		double f = f(x-1,a);
		double d = derivada_f(x-1);
		if(Math.abs(f) < Math.pow(10, -6)){
			return x;
		}
		double r = raizcuadrada(x-1, a);
		return r -(f/d);
	}
	
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		sc.close();
		System.out.println(raizcuadrada(a,a));
	}
}
