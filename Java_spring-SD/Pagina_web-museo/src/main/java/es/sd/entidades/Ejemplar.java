package es.sd.entidades;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Ejemplar {
	@Id @GeneratedValue(strategy=GenerationType.AUTO) private int id;
	@ManyToOne private Moneda coin;
	@ManyToOne private Proveedor provider;
	
	private int year;
	private String city;
	private Date date;
	private String state;
	private String model;	
	
	public Ejemplar() {}
	
	public Ejemplar(Moneda coin, String model, Proveedor provider, int year, String city, Date date, String state) {
		this.setCoin(coin);
		this.setModel(model);
		this.setProvider(provider);
		this.setYear(year);
		this.setCity(city);
		this.setDate(date);
	}

	public int getId() {
		return this.id;
	}
	
	public Proveedor getProvider(){
		return this.provider;	
	}
	
	public void setProvider(Proveedor p) {
		this.provider = p;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public Moneda getCoin() {
		return this.coin;	
	}
	
	public void setCoin(Moneda coin) {
		this.coin = coin;	
	}

	public String getModel() {
		return this.model;
	}
	
	public void setModel(String modelo) {
		this.model = modelo;
	}
	
	public String getImagen() {
		String s= this.coin.getFace()+"-"+this.coin.getValue();
		return s;
	}	
	
	@Override
	public String toString() {
		String s = " Modelo: "+this.getModel()
		+"\n Proveedor: "+getProvider()
		+"\n Año y Ciudad de Acuñación: "+getYear()+" "+getCity()
		+"\n Fecha de Adquisición:"+ getDate()
		+"\n Estado: "+getState();
		return s;
	}
}