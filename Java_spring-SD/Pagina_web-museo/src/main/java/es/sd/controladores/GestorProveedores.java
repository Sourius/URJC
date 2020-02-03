package es.sd.controladores;

import java.util.List;

//para proyecto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//para bd
import es.sd.entidades.Proveedor;
import es.sd.repositorios.RepositorioProveedor;

@Controller
public class GestorProveedores {
	@Autowired
	private RepositorioProveedor providers;
	
	@RequestMapping("/proveedor")
	public String proveedor(
			@RequestParam String nif,
			@RequestParam String name,
			@RequestParam String address,
			@RequestParam String email,
			@RequestParam int number, 
			Model model) {
		Proveedor p;
		p = new Proveedor(nif,name,address,email,number);
		providers.save(p);
		return "mensaje";
	}
	
	@RequestMapping("/insertarProveedor")
	public String insertar(Model model) {		
		return "insertarProveedor";
	}
	
	@RequestMapping("/modificarProveedor")
	public String modificarProveedores(
			@RequestParam String nif,
			@RequestParam String name,
			@RequestParam String address,
			@RequestParam String email,
			@RequestParam int number, 
			
			Model model) {
		Proveedor p;
		
		try {
			p = providers.findByNif(nif);
		}
		catch(Exception e) {
			p=new Proveedor();
			p.setNIF(nif);
		}
		p.setName(name);
		p.setAddress(address);
		p.setEmail(email);
		p.setNumber(number);
		providers.save(p);
		
		model.addAttribute("proveedores",providers.findAll());	
		return "mostrarProveedores";
	}
	
	@RequestMapping("/verProveedor")
	public String mostrarProveedor(Model model) {
		List<Proveedor> aux = providers.findAll();
		
		if(aux.isEmpty()) {
			return "mensaje_vacio";
		}
		else {
			model.addAttribute("proveedores",aux);	
			return "mostrarProveedores";
		}
	}
}