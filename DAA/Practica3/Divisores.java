package practicas.practica3;

import java.util.Scanner;

public class Divisores {	
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
	
	static int getMin(int sub[], int pos, int v[]){
		int min = v[sub[0]];
		for(int i = 1; i < pos; i++) {
			if(v[sub[i]]<min) min = v[sub[i]];
		}
		return min;
	}
	
	static boolean isSolution(int sub[], int m, int v[]){
		int min = getMin(sub,m,v);
		for(int i = 0; i < m; i++){
			if((v[sub[i]] % min) != 0) return false;
		}
		return true;
	}
	
	static int subColeccion(int v[], int n, int m, int sub[], int i, int j){
		// generar permutacion de elementos sin repetir
		int res = 0;
		if(i == m){
			if(isSolution(sub,m,v)) res = 1;
		}
		else{
			for(int k = j+1; k<n; k++){				
				sub[i] = k;
				res += subColeccion(v,n,m,sub,i+1,k);
			}
		}
		return res;
	}
	
	public static void main(String[] args) {
		Scanner sc;
		int n, m, v [];
		int sub[], res;
		String aux;
		
		sc = new Scanner(System.in);
		n = sc.nextInt();
		// saltar \n
		sc.nextLine();
		// leer lista en formato string
		aux = sc.nextLine();
		v = getList(aux, n);
		
		if(v != null){
			m = sc.nextInt();
			sc.close();
			if(m > 0 && m <= n){
				sub = new int[m];
				System.out.println(subColeccion(v,n,m,sub,0,-1));
			}
		}
	}
}
