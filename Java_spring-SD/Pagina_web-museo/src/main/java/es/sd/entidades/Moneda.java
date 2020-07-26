package es.sd.entidades;

//para la bd
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Moneda {
	@Id @GeneratedValue(strategy=GenerationType.AUTO) private int id;
	private String model; //modelo
	private String face; //cara
	private String value; //unidad
	private float weight; //peso
	private float diameter; //tamaño
	private ArrayList<String> component; //composición
	private String desc; // descripción
	
	@OneToMany private List<Ejemplar> samples;
	
	//constructores
	public Moneda() {}
	public Moneda(String model, String face, String value, float diameter, float weight, String component, String desc) {
		this.setModel(model);
		this.setFace(face);
		this.setValue(value);
		this.setDiameter(diameter);
		this.setWeight(weight);
		this.setComponents(component);
		this.setDesc(desc);
	}

	/*public int getId() {
		return this.id;
	}*/
	
	public String getModel() {
		return this.model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getComponents() {
		String s = "";
		for(String aux: this.component) {
			s= s+" "+aux;
		}
		return s;
	}

	public void setComponents(String component) {
		this.component = new ArrayList<>();
		String [] s = component.split(",");
		for(int i = 0; i < s.length; i++) {
			this.component.add(s[i]);
		}
	}
	
	public void setComponents(List<String> component) {
		this.component = new ArrayList<>();
		this.component.addAll(component);
	}

	public String getFace() {
		return this.face;
	}

	public void setFace(String face){
		this.face = face;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public boolean hasComponent(String component) {
		for(String s: this.component) {
			if(s.compareTo(component) == 0) return true;
		}
		return false;
	}
	
	public float getDiameter() {
		return diameter;
	}

	public void setDiameter(float diameter) {
		this.diameter = diameter;
	}
	
	@Override
	public String toString() {
		String s = 
				"Modelo: "+getModel()+
				"\nCara: "+getFace()+
				"\nUnidad: "+getValue()+
				"\nPeso: "+getWeight()+" gramos "+
				"\nTamaño: "+getDiameter()+
				"\nDescripción: "+getDesc()+"\n";
		return s;
	}

}
