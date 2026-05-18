package cl.duoc.backend_api_oferta_turismo.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.backend_api_oferta_turismo.Modelo.Oferta;
import java.util.Optional;
public interface OfertaRepository extends JpaRepository<Oferta,Long>{

Optional<Oferta> findByNombreOfertaIgnoreCase(String nombreOferta);

    boolean existsByNombreOfertaIgnoreCase(String nombreOferta);

    @Transactional
    void deleteByNombreOfertaIgnoreCase(String nombreOferta);
}
