package es.urjc.ssii.code.repositorios;

import es.urjc.ssii.code.entidades.Empresa;
import es.urjc.ssii.code.entidades.Oferta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "offers", path = "offers")
public interface RepositorioOferta extends CrudRepository<Oferta, Integer> {
	Oferta findById(int id);
	List<Oferta> findByCompany(Empresa company);
	List <Oferta> findByVisible(boolean visible);
	List <Oferta> findByDueDate(Date dueDate);
	List <Oferta> findByNumApplicantsOrderByCompanyNameAscTitleAsc(int numApplicants);
	List<Oferta> findByOrderByNumApplicantsDesc();
	List<Oferta> findByCity(String city);
	List<Oferta> findByProvince(String province);
}
