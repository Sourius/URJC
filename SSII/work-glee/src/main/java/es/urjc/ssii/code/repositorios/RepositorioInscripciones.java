package es.urjc.ssii.code.repositorios;

import es.urjc.ssii.code.entidades.Demandante;
import es.urjc.ssii.code.entidades.Oferta;
import es.urjc.ssii.code.entidades.RegistroOferta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "inscriptions", path = "inscriptions")
public interface RepositorioInscripciones extends CrudRepository<RegistroOferta, Integer> {
	<Optional>RegistroOferta findByRegisterId(int registeredId);
	List<RegistroOferta> findByRegisteredAt(Date registeredAt);
	List<RegistroOferta> findBySelectedForInterview(boolean selectedForInterview);
	List<RegistroOferta> findByExpired(boolean expired);
	List<RegistroOferta> findByApplicant(Demandante applicant);
	List<RegistroOferta> findByOffer(Oferta offer);
}
