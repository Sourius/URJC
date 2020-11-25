package es.sd.controladores;

import java.sql.Date;
import java.util.List;

//para proyecto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//para bd
import es.sd.entidades.Ejemplar;
import es.sd.entidades.Moneda;
import es.sd.entidades.Proveedor;
import es.sd.repositorios.RepositorioEjemplar;
import es.sd.repositorios.RepositorioMonedas;
import es.sd.repositorios.RepositorioProveedor;

@Controller
public class GestorEjemplares{
	@Autowired private RepositorioEjemplar samples;
	@Autowired RepositorioMonedas coins;
	@Autowired RepositorioProveedor providers;
	
	@RequestMapping("/ejemplar")
	public String ejemplar(
			@RequestParam(value="model") String modelo,
			@RequestParam(value="year") int year,
			@RequestParam(value="city") String city,
			@RequestParam(value="date") Date date,
			@RequestParam(value="state") String state,
			@RequestParam(value="provider") String nif,
			Model model) {
		Proveedor p;
		Moneda m;
		Ejemplar e;

		e = new Ejemplar();
		try {
			m = coins.findByModel(modelo);
		}
		catch(Exception ex) {
			m = new Moneda();
			m.setModel(modelo);
			coins.save(m);
		}
		try {
			p = providers.findByNif(nif);
		}
		catch(Exception ex) {
			p = new Proveedor();
			p.setNIF(nif);
			providers.save(p);
		}
		
		e.setModel(modelo);
		e.setYear(year);
		e.setCity(city);
		e.setDate(date);
		e.setState(state);
		e.setCoin(m);
		e.setProvider(p);
		
		//guardar
		samples.save(e);
		return("mensaje");
	}
	
	@RequestMapping("/modificarEjemplar")
	public String mostrarEjemplar(
			@RequestParam(value="model") String modelo,
			@RequestParam(value="year") int year,
			@RequestParam(value="city") String city,
			@RequestParam(value="date") Date date,
			@RequestParam(value="state") String state,
			@RequestParam(value="provider") String nif,
			Model model){
		
		Ejemplar e;
		Proveedor p;
		Moneda m;
		try {
			e= samples.findByModel(modelo);	
		}
		catch(Exception ex) {
			e = new Ejemplar();
		}
		
		try {
			m = coins.findByModel(modelo);
		}
		catch(Exception ex) {
			m = new Moneda();
			m.setModel(modelo);
			coins.save(m);
		}
		
		try {
			p = providers.findByNif(nif);
		}
		catch(Exception ex) {
			p = new Proveedor();
			p.setNIF(nif);
			providers.save(p);
		}
		
		e.setModel(modelo);
		e.setYear(year);
		e.setCity(city);
		e.setDate(date);
		e.setState(state);	
		e.setCoin(m);
		e.setProvider(p);	
		
		samples.save(e);
		model.addAttribute("ejemplares",samples.findAll());	
		return "mostrarEjemplares";
	}
	
	@RequestMapping("/")
	public String mostrar(Model model) {
		model.addAttribute("ejemplares",samples.findAll());	
		return("museo");
	}
	
	@RequestMapping("/verEjemplar")
	public String mostrarEjemplares(Model model) {
		List<Ejemplar> aux = samples.findAll();
		if(aux.isEmpty()) {
			return "mensaje_vacio";
		}
		else {	
			model.addAttribute("ejemplares",aux);	
			return("mostrarEjemplares");
		}
	}
	
	@RequestMapping("/insertarEjemplar")
	public String insertarEjemplar(Model model) {		
		return "insertarEjemplar";
	}
}