package es.sd.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import es.sd.entidades.Moneda;

public interface RepositorioMonedas extends JpaRepository<Moneda, Long>{	
	//consultas por tipo
	//Moneda findById(int id);
	Moneda findByModel(String model);
	List<Moneda> findByFace(String face);
	List<Moneda> findByValue(String value);
	List<Moneda> findByWeight(float weight);
	List<Moneda> findByComponent(List<String> component);
	
	//consultas por tipo ordenadas
	List<Moneda> findOrderedByModel(String model);
	List<Moneda> findOrderedByFace(String face);
	List<Moneda> findOrderedByValue(String value);
	List<Moneda> findOrderedByWeight(float weight);
	List<Moneda> findOrderedByComponent(List<String> component);

}