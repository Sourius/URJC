package es.urjc.ssii.code.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Empresa extends Usuario {
	private String companyName;
	private String description;

	@OneToMany(mappedBy = "company")
	@JsonManagedReference
	private List<Oferta> offers;
	private int numOffers;

	public Empresa() {
		super();
		setProfile(USERTYPE.COMPANY);
		offers = new ArrayList<>();
		numOffers = 0;
	}

	public Empresa(String companyName){
		this();
		setCompanyName(companyName);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Oferta> getOffers() {
		return offers;
	}

	public void setOffers(List<Oferta> offers) {
		this.offers = offers;
	}

	public int getNumOffers() {
		return numOffers;
	}

	public void setNumOffers(int numOffers) {
		this.numOffers = numOffers;
	}

	public void refreshNumOffers(){
		setNumOffers(offers.size());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Empresa{");

		sb.append("id=" + getUserId() +
				", companyName='" + getCompanyName() + '\'' +
				", description='" + getDescription() + '\'' +
				", username='" + getEmail() + '\'' +
				", password='" + "**********" + '\'' +
				", city='" + getCity() + '\'' +
				", province='" + getProvince() + '\'' +
				", offers=" + getOffers() +
				"}");
		return sb.toString();
	}
}
