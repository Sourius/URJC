package es.urjc.ssii.code.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Oferta implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int offerId;
	private String title;
	private String description;
	private double income;
	private String city;
	private String province;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dueDate;
	private boolean visible;
	private int numApplicants;

	@ManyToOne
	@JoinColumn(name = "company_id")
	@JsonBackReference
	private Empresa company;

	@OneToMany(mappedBy = "offer")
	@JsonManagedReference
	private List<RegistroOferta> applicants;

	public Oferta() {
		applicants = new ArrayList<>();
		numApplicants = 0;
	}

	public Oferta(String title, String description) {
		this();
		setTitle(title);
		setDescription(description);
	}

	public int getOfferId() {
		return offerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
		this.dueDate = format.parse(dueDate);
	}

	public void setDueDate(Date dueDate) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
		this.dueDate = format.parse(format.format(dueDate));
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Empresa getCompany() {
		return company;
	}

	public void setCompany(Empresa empresa) {
		this.company = empresa;
	}

	public int getNumApplicants() {
		refreshNumApplicants();
		return numApplicants;
	}

	public void setNumApplicants(int n){
		numApplicants = n;
	}

	public void refreshNumApplicants(){
		setNumApplicants(applicants.size());
	}

	@Override
	public String toString() {
		return "Oferta{" +
				"id='"+getOfferId()+'\''+
				", title='" + getTitle() + '\'' +
				", description='" + getDescription() + '\'' +
				", income=" + getIncome() +
				", address='" + getCity() +", "+ getProvince()+ '\'' +
				", dueDate='" + getDueDate() + '\'' +
				", visible=" + isVisible() +
				", empresa=" + getCompany().getCompanyName() +
				'}';
	}
}
