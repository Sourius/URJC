package es.urjc.ssii.code.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
public class Demandante extends Usuario {
	private Date birthday;
	private int numOffers;

	@OneToMany(mappedBy = "applicant")
	@JsonManagedReference
	private List<RegistroOferta> inscriptions;

	public Demandante() {
		super();
		setProfile(USERTYPE.USER);
	}

	public Demandante (Usuario user, String date) throws ParseException {
		this();
		setBirthday(date);
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
		this.birthday = format.parse(birthday);
	}

	public List<RegistroOferta> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(List<RegistroOferta> inscriptions) {
		this.inscriptions = inscriptions;
	}

	public int getNumOffers() {
		refreshNumOffers();
		return numOffers;
	}

	public void setNumOffers(int numOffers) {
		this.numOffers = numOffers;
	}

	public void refreshNumOffers(){
		setNumOffers(inscriptions.size());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Demandante{");
		sb.append("id=" + getUserId() +
				", name='" + getName() + '\'' +
				", surname='" + getSurname() + '\'' +
				", birthday='" + getBirthday() + '\'' +
				", username='" + getEmail() + '\'' +
				", password='" + "**********" + '\'' +
				"}");

		return sb.toString();
	}
}
