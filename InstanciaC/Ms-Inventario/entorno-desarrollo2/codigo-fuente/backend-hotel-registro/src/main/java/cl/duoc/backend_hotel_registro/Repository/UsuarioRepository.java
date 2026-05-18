package cl.duoc.backend_hotel_registro.Repository;
import cl.duoc.backend_hotel_registro.Model.Usuario;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;





@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

    Optional<Usuario>findByGmail(String gmail);
    Optional<Usuario> findByRut(String rut);
    boolean existsByRutIgnoreCase(String rut);

   @Transactional
   void deleteByRutIgnoreCase(String rut);
}
