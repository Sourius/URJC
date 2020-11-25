package es.urjc.ssii.code.gestores;

import es.urjc.ssii.code.entidades.Usuario;
import es.urjc.ssii.code.repositorios.RepositorioInscripciones;
import es.urjc.ssii.code.repositorios.RepositorioOferta;
import es.urjc.ssii.code.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@Autowired
	RepositorioOferta offers;
	@Autowired
	RepositorioInscripciones inscriptions;

	@RequestMapping(value = "/")
	public String mainPage(Model model){
		return sendToFrontPage(model);
	}

	private String sendToFrontPage(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
		Usuario user = details.getUser();

		model.addAttribute("id",user.getUserId());
		if(user.isAdmin()) return "/adminPage";
		else if(user.isCompany()) return "/companyPage";
		else return "/userPage";

	}
}
