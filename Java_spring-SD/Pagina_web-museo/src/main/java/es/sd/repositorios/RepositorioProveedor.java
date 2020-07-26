package es.sd.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import es.sd.entidades.Proveedor;

public interface RepositorioProveedor extends JpaRepository<Proveedor, Long>{	
	//consultas por tipo
	Proveedor findById(int id);
	Proveedor findByNif(String nif);
	List<Proveedor> findByName(String nombre);
	Proveedor findByAddress(String address);
	Proveedor findByEmail(String email);
	Proveedor findByNumber(int number);
	
	//consultas ordenadaas por tipo
	List<Proveedor> findOrderedByNif(String nif);
	List<Proveedor> findOrderedByName(String name);
	List<Proveedor> findOrderedByAddress(String address);
	List<Proveedor> findOrderedByEmail(String email);
	List<Proveedor> findOrderedByNumber(int number);
}