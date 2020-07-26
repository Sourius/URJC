package es.urjc.ssii.code.entidades;

import javax.persistence.Entity;

@Entity
public class Admin extends Usuario {
	public Admin() {
		super();
		setProfile(USERTYPE.ADMIN);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Admin{");

		sb.append("id=" + getUserId() +
				", name='" + getName() + '\'' +
				", surname='" + getSurname() + '\'' +
				", username='" + getEmail() + '\'' +
				", password='" + "**********" + '\'' +
				"}");

		return sb.toString();
	}
}
