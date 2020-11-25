package es.sd.controladores;

import java.util.List;

//para proyecto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//para bd
import es.sd.entidades.Moneda;
import es.sd.repositorios.RepositorioMonedas;

@Controller
public class GestorMonedas {
	@Autowired 
	private RepositorioMonedas coins;
	
	@RequestMapping("/moneda")
	public String moneda(
			@RequestParam(value="model") String id,
			@RequestParam String face,
			@RequestParam(value="value") String value,
			@RequestParam float diameter,
			@RequestParam float weight,
			@RequestParam String components,
			@RequestParam String desc,
			Model model) {
		
		Moneda m = new Moneda();
		//añadir valores
		m.setModel(id); // modelo
		m.setFace(face); //cara
		m.setValue(value); //unidad
		m.setDiameter(diameter); //tamaño
		m.setWeight(weight); //peso
		m.setComponents(components); //composición
		m.setDesc(desc); // descripción
		
		coins.save(m);
		return("mensaje");
	}
	
	@RequestMapping("/modificarMoneda")
	public String modificarMoneda(
			@RequestParam(value="model") String modelo,
			@RequestParam String face,
			@RequestParam(value="value") String value,
			@RequestParam float diameter,
			@RequestParam float weight,
			@RequestParam String components,
			@RequestParam String desc,
			
			Model model) {
		Moneda m; 
		m = coins.findByModel(modelo);
		m.setFace(face); //valor facial
		m.setValue(value); //valor (euro, dolar , ...)
		m.setDiameter(diameter); //tamaño
		m.setWeight(weight); //peso
		m.setComponents(components); //composición
		m.setDesc(desc); // descripción
		coins.save(m);
		
		model.addAttribute("monedas",coins.findAll());	
		return("mostrarMonedas");
	}
	
	@RequestMapping("/verMoneda")
	public String mostrarMonedas(Model model) {	
		List<Moneda> aux = coins.findAll();
		if(aux.isEmpty()) {
			return "mensaje_vacio";
		}
		else {
			model.addAttribute("monedas",aux);	
			return "mostrarMonedas";
		}	
	}
	
	@RequestMapping("/insertarMoneda")
	public String insertar(Model model) {		
		return "insertarMoneda";
	}
}