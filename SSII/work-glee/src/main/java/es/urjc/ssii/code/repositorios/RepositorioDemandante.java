package es.urjc.ssii.code.repositorios;

import es.urjc.ssii.code.entidades.Demandante;
import es.urjc.ssii.code.entidades.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface RepositorioDemandante extends CrudRepository<Demandante, Integer> {
	<Optional> Demandante findByEmail(String username);
	<Optional> Demandante findByUserId(int id);
	List<Demandante> findByName(String name);
	List<Demandante> findBySurname(String surname);
	List<Demandante> findByProfile(Usuario.USERTYPE profile);
	List<Demandante> findByBirthday(Date birthday);
	List<Demandante> findByOrderByNumOffersDesc();
}
