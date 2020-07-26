package es.urjc.ssii.code.gestores;

import es.urjc.ssii.code.entidades.Demandante;
import es.urjc.ssii.code.entidades.Oferta;
import es.urjc.ssii.code.repositorios.RepositorioDemandante;
import es.urjc.ssii.code.repositorios.RepositorioOferta;
import es.urjc.ssii.code.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
	@Autowired
	RepositorioOferta offers;
	@Autowired
	RepositorioDemandante users;

	@RequestMapping(value = "/offersByNumInscriptions")
	public List<Oferta> topOffers(){
		checkAdmin();
	 	return offers.findByOrderByNumApplicantsDesc();
	}

	@RequestMapping(value = "/bottomOffers")
	public List<Oferta> bottomOffers(){
		checkAdmin();
		return offers.findByNumApplicantsOrderByCompanyNameAscTitleAsc(0);
	}

	@RequestMapping(value = "/usersByNumOffers")
	public List<Demandante> usersOrderedByNumOffers(){
		checkAdmin();
		return users.findByOrderByNumOffersDesc();
	}

	@RequestMapping(value = "/offersByProvince/")
	public List<Oferta> offersOrderedByprovince(
			@PathVariable(name = "province") String province
	){
		checkAdmin();
		return offers.findByProvince("%"+province);
	}

	private boolean checkAdmin(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated()) {
			UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
			if(details.getUser().isAdmin()) return true;
		}
		return false;
	}
}
