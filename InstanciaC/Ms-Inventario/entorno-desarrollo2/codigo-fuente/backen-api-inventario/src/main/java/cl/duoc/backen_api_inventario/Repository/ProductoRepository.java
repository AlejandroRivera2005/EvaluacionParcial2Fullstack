package cl.duoc.backen_api_inventario.Repository;
import cl.duoc.backen_api_inventario.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto,Long>{
Optional<Producto> findByCodigo(String codigo);
    

    boolean existsByCodigoIgnoreCase(String codigo); 

    @Transactional
    void deleteByCodigoIgnoreCase(String codigo);
}
