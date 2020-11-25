package practicas.practica1;

import java.util.Scanner;

public class RecursiveInsertSort {
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		sc.nextLine();
		String elementos = sc.nextLine();
		sc.close();
		int v[], sol[];
		v = ListaOrdenada.getList(elementos);
		sol = new int[1];
		sol[0] = v[0];
		for(int i = 1; i < n; i++){
			sol = ListaOrdenada.insertar(i, sol, v[i]);
		}
		ListaOrdenada.printSolution(sol, n);
	}
}
