package es.urjc.ssii.code.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class RegistroOferta implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int registerId;
	private Date registeredAt;
	private boolean selectedForInterview;
	private boolean expired;
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "user_id")
	private Demandante applicant;
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "offer_id")
	private Oferta offer;

	public RegistroOferta(){}

	public RegistroOferta(Demandante user, Oferta offer) throws ParseException {
		this();
		setApplicant(user);
		setOffer(offer);
		setRegisteredAt(null);
		setExpired(false);
		setSelectedForInterview(false);
	}

	public int getRegisterId() {
		return registerId;
	}

	public Date getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(String registeredAt) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if(registeredAt == null) this.registeredAt = format.parse(format.format(new Date()));
		else this.registeredAt = format.parse(registeredAt);
	}

	public boolean isSelectedForInterview() {
		return selectedForInterview;
	}

	public void setSelectedForInterview(boolean selectedForInterview) {
		this.selectedForInterview = selectedForInterview;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public Demandante getApplicant() {
		return applicant;
	}

	public void setApplicant(Demandante applicant) {
		this.applicant = applicant;
	}

	public Oferta getOffer() {
		return offer;
	}

	public void setOffer(Oferta offer) {
		this.offer = offer;
	}

	@Override
	public String toString() {
		return "RegistroOferta{" +
				"id=" + getRegisterId() +
				", user_id=" + getApplicant().getUserId() +
				", offer_id=" + getOffer().getOfferId() +
				", registeredAt=" + getRegisteredAt() +
				", selectedForInterview=" + isSelectedForInterview() +
				", expired=" + isExpired() +
				'}';
	}
}
