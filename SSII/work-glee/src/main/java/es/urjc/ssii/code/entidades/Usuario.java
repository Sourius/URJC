package es.urjc.ssii.code.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class Usuario implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	private String name;
	private String surname;
	private String email;
	@JsonIgnore private String password;
	private String city;
	private String province;

	public enum USERTYPE {USER, COMPANY, ADMIN}
	@Enumerated(EnumType.STRING)
	private USERTYPE profile;

	public Usuario(){}

	public Usuario(String name, String surname){
		this();
		setName(name);
		setSurname(surname);
	}

	public Usuario(String name, String username, String password){
		this();
		setName(name);
		setEmail(username);
		setPassword(password);
	}

	public int getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public String getUserName() {
		return getEmail();
	}

	public void setEmail(String email) {
		if (this.email == null) this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public USERTYPE getProfile() {
		return profile;
	}

	public void setProfile(USERTYPE profile) {
		this.profile = profile;
	}

	public boolean isAdmin(){
		return USERTYPE.ADMIN == getProfile();
	}

	public boolean isCompany(){
		return USERTYPE.COMPANY == getProfile();
	}

	public boolean isUser(){
		return USERTYPE.USER == getProfile();
	}
}
