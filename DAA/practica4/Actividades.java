package practicas.practica4;

//import java.util.Arrays;
import java.util.Scanner;

public class Actividades {
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
	
	//factibilidad: comprobar si la actividad es compatible
	static boolean isCompatible(int end, int ti){
		return  end <= ti;
	}
	
	static int[] orderElements(int n, int ti[], int tf[]){
		int temp, aux[];
		aux = new int[n];
		
		for(int i = 0; i < n; i++){
			aux[i] = i;
			for(int j = i; j > 0; j--){	
				if(tf[aux[j]] < tf[aux[j-1]]) {
					temp = aux[j];
					aux[j] = aux[j-1];
					aux[j-1] = temp;
				}
			}
		}
		return aux;
	}
	
	static int activitySelection(int n, int ti[], int tf[], int aux[]){
		int end, res;
		end = tf[aux[0]];
		res = 1;
		for(int i = 1; i < n; i++){
			if(isCompatible(end,ti[aux[i]])){
				res++;
				end = tf[aux[i]];
			}
		}
		return res;
	}
	
	public static void main(String args[]){
		Scanner sc;
		int n, ti[], tf[], res;
		int aux[];
		String auxi, auxf;
		
		sc = new Scanner(System.in);
		n = sc.nextInt();
		// saltar \n
		sc.nextLine();
		// leer los tiempos de inicio en formato string
		auxi = sc.nextLine();
		ti = getList(auxi, n);
		
		if(ti != null){
			auxf = sc.nextLine();
			//leer los tiempos de final
			tf = getList(auxf, n);
			sc.close();
			//si coinciden el numero de elementos
			if(tf != null){
				aux = orderElements(n,ti,tf);
				//System.out.println(Arrays.toString(aux));
				res = activitySelection(n,ti,tf,aux);
				System.out.println(res);
			}
		}
	}
}
