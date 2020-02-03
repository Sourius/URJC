package es.sd.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Proveedor {
	@Id @GeneratedValue(strategy=GenerationType.AUTO) private int id;
	private String nif;
	@JoinColumn private String name;
	private String address;
	private String email;
	private int number;
	@OneToMany (mappedBy="provider") 
	private List<Ejemplar> samples;
	
	public Proveedor () {}
	public Proveedor(String nIF, String name, String address, String email, int number) {
		this.nif = nIF;
		this.name = name;
		this.address = address;
		this.email = email;
		this.number = number;	
	}

	public int getId() {
		return this.id;
	}
	
	public String getNIF() {
		return nif;
	}
	
	public void setNIF(String nIF) {
		this.nif = nIF;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int compareTo(Proveedor provider) {
		return this.nif.compareToIgnoreCase(provider.getNIF());
	}
	
	@Override
	public String toString() {
		if(this.name == null) return this.getNIF();
		else return this.getName();
	}
}
