package cl.duoc.catalogo.repository;

import cl.duoc.catalogo.model.Catalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository indica a Spring que este componente se encarga de la base de datos.
 * Al heredar de JpaRepository, le decimos qué clase manejar (Libro) y qué tipo de dato es su ID (Long).
 * Esto nos regala automáticamente métodos como save(), findAll(), findById(), deleteById().
 */
@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
}