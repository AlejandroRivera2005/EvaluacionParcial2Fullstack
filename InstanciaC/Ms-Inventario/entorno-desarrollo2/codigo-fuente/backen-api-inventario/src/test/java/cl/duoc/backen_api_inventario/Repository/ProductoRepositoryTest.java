package cl.duoc.backen_api_inventario.Repository;
import cl.duoc.backen_api_inventario.Model.Producto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;
    @Test
    @DisplayName("save - debe persistir el producto y asignar un ID generado automáticamente")
    void debePersistirProductoYAsignarIdGenerado() {
        // Given
        Producto producto = new Producto(null,"COD898","Sabanas", "Hogar", 15, 29.99);

        // When
        Producto guardado = productoRepository.save(producto);

        // Then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals("Sabanas", guardado.getNombre());
        assertEquals(15, guardado.getStock());
    }
    @Test
    @DisplayName("findAll - debe retornar todos los productos guardados en la BD")
    void debeRetornarTodosLosProductosGuardados() {
        // Given
        Producto producto1 = new Producto(null,"COD123", "Producto A", "Categoria1", 10, 22.5);
        Producto producto2 = new Producto(null,"COD456", "Producto B", "Categoria2", 20, 45.0);
        productoRepository.save(producto1);
        productoRepository.save(producto2);

        // When
        List<Producto> productos = productoRepository.findAll();

        // Then
        assertNotNull(productos);
        assertEquals(2, productos.size());
    }
    
    @Test
    @DisplayName("findById - debe retornar el producto correcto cuando el ID existe")
    void debeEncontrarProductoPorIdExistente() {
        // Given
        Producto guardado = productoRepository.save(new Producto(null,"COD789", "Producto C", "Categoria3", 30, 67.5));
        Optional<Producto> resultado = productoRepository.findById(guardado.getId());
        // Then
        assertTrue(resultado.isPresent());
        assertEquals(guardado.getId(), resultado.get().getId());
        assertEquals("Producto C", resultado.get().getNombre());
    }
    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
         // When
        Optional<Producto> resultado = productoRepository.findById(999L);
        // Then
        assertFalse(resultado.isPresent());
    }
     @Test
    @DisplayName("deleteById - debe eliminar el producto de la base de datos")
    void debeEliminarProductoPorId() {
        // Given
        Producto guardado = productoRepository.save(new Producto(null,"COD789", "Producto C", "Categoria3", 30, 67.5));
        Long id = guardado.getId();
        // When
        productoRepository.deleteById(id);
        // Then
        assertFalse(productoRepository.findById(id).isPresent());
    }
}
