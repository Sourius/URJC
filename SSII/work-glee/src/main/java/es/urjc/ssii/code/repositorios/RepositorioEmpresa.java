package es.urjc.ssii.code.repositorios;

import es.urjc.ssii.code.entidades.Empresa;
import es.urjc.ssii.code.entidades.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "companies", path = "companies")
public interface RepositorioEmpresa extends CrudRepository<Empresa,Integer> {
	<Optional>Empresa findByEmail(String username);
	<Optional> Empresa findByUserId(int id);
	List<Empresa> findByName(String name);
	List<Empresa> findBySurname(String surname);
	List<Empresa> findByProfile(Usuario.USERTYPE profile);
	List<Empresa> findByCompanyName(String companyName);
	List<Empresa> findByCity(String city);
	List<Empresa> findByProvince(String province);
}
