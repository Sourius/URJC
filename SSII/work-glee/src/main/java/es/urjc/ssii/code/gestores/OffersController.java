package es.urjc.ssii.code.gestores;

import es.urjc.ssii.code.entidades.Empresa;
import es.urjc.ssii.code.entidades.Oferta;
import es.urjc.ssii.code.repositorios.RepositorioInscripciones;
import es.urjc.ssii.code.repositorios.RepositorioOferta;
import es.urjc.ssii.code.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class OffersController {
	@Autowired
	RepositorioOferta offers;
	@Autowired
	RepositorioInscripciones inscriptions;

	@CrossOrigin
	@PostMapping(value = "/addNewOffer")
	public void addOffer(
			@RequestParam(name = "title") String title,
			@RequestParam(name = "description") String desc,
			@RequestParam(name = "income") double income,
			@RequestParam(name = "city") String city,
			@RequestParam(name = "province") String province,
			@RequestParam(name = "due_date") String date,
			@RequestParam(name = "visible", required = false) boolean visible) throws ParseException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
		Empresa e = (Empresa) details.getUser();

		Oferta newOffer = new Oferta();
		newOffer.setTitle(title);
		newOffer.setDescription(desc);
		newOffer.setCity(city);
		newOffer.setProvince(province);
		newOffer.setIncome(income);
		newOffer.setCompany(e);
		newOffer.setDueDate(dateConverter(date));
		//newOffer.setDueDate(date);
		newOffer.setVisible(visible);
		offers.save(newOffer);
	}

	@CrossOrigin
	@PutMapping(value = "/modify_offer/{id}")
	public void modify_offer(
			@PathVariable (name = "id") int id,
			@RequestParam(name = "title") String title,
			@RequestParam(name = "description") String desc,
			@RequestParam(name = "income") double income,
			@RequestParam(name = "city") String city,
			@RequestParam(name = "province") String province,
			@RequestParam(name = "due_date", required = false) String date,
			@RequestParam(name = "visible", required = false) boolean visible) throws ParseException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
		Empresa e = (Empresa) details.getUser();

		Oferta oldOffer;
		oldOffer = offers.findById(id);

		if(checkOffer(oldOffer,e)) {
			oldOffer.setTitle(title);
			oldOffer.setDescription(desc);
			oldOffer.setCity(city);
			oldOffer.setProvince(province);
			oldOffer.setCompany(e);
			oldOffer.setIncome(income);
			if (!date.isEmpty()) oldOffer.setDueDate(dateConverter(date));
			//oldOffer.setDueDate(date);
			oldOffer.setVisible(visible);
			offers.save(oldOffer);
		}
	}

	@CrossOrigin
	@DeleteMapping(value = "/deleteOffer/{id}")
	public void delete(@PathVariable int id){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
		Empresa e = (Empresa) details.getUser();
		Oferta oldOffer;
		oldOffer = offers.findById(id);
		if(checkOffer(oldOffer,e)) {
			offers.deleteById(id);
		}
	}

	private String dateConverter(String date){
		String aux[] = date.substring(0,10).split("-");
		String newDate = aux[2]+"/"+aux[1]+"/"+aux[0];
		return newDate;
	}

	private boolean checkOffer(Oferta offer, Empresa company){
		if(offer == null) return false;
		return offer.getCompany().getUserId() == company.getUserId();
	}
}
