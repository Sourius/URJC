package es.urjc.ssii.code.repositorios;

import es.urjc.ssii.code.entidades.Admin;
import es.urjc.ssii.code.entidades.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "admins", path = "admins")
public interface RepositorioAdmin extends CrudRepository<Admin, Integer> {
	<Optional> Admin findByEmail(String username);
	<Optional> Admin findByUserId(int id);
	List<Admin> findByName(String name);
	List<Admin> findBySurname(String surname);
	List<Admin> findByProfile(Usuario.USERTYPE profile);
}
