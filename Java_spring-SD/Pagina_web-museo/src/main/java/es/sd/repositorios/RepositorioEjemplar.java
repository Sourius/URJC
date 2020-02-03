package es.sd.repositorios;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import es.sd.entidades.Ejemplar;
import es.sd.entidades.Proveedor;

public interface RepositorioEjemplar extends JpaRepository<Ejemplar, Long>{
	//consultas por tipo
	Ejemplar findById(int id);
	Ejemplar findByModel(String model);
	List<Ejemplar> findByProvider(Proveedor provider);
	List<Ejemplar> findByYearAndCity(Date year, String city);
	List<Ejemplar> findByDate(Date date);
	List<Ejemplar> findByState(String state);

	// consultas ordenadas por tipo
	List<Ejemplar> findOrderedById(int id);
	List<Ejemplar> findOrderedByModel(String model);
	List<Ejemplar> findOrderedByProvider(Proveedor provider);
	List<Ejemplar> findOrderedByYearAndCity(Date year, String city);
	List<Ejemplar> findOrderedByDate(Date date);
	List<Ejemplar> findOrderedByState(String state);
}