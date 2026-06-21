package cl.duoc.backen_api_inventario.Model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;





public class ProductoTest {
 @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        Producto producto = new Producto();
        assertNotNull(producto);
    }

     @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        Producto producto = new Producto(
             1L,"COD123", "Producto de prueba", "Categoria1", 10, 22.5
        );
        assertEquals(1L, producto.getId());
        assertEquals("COD123", producto.getCodigo());
        assertEquals("Producto de prueba", producto.getNombre());
        assertEquals("Categoria1", producto.getCategoria());
        assertEquals(10, producto.getStock());
        assertEquals(22.5, producto.getPrecio(), 0.01);
    }
      @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {
        Producto producto = new Producto();

        producto.setId(2L);
        producto.setCodigo("COD456");
        producto.setNombre("Otro producto");
        producto.setCategoria("Categoria2");
        producto.setStock(20);
        producto.setPrecio(45.0);
        assertEquals(2L, producto.getId());
        assertEquals("COD456", producto.getCodigo());
        assertEquals("Otro producto", producto.getNombre());
        assertEquals("Categoria2", producto.getCategoria());
        assertEquals(20, producto.getStock());
        assertEquals(45.0, producto.getPrecio(), 0.01);

        

        
    }
    @Test
    @DisplayName("equals y hashCode - dos productos con los mismos datos deben ser iguales")
    void dosProductosConMismosDatosDebenSerIguales() {
        Producto p1 = new Producto(1L,"COD123", "Producto de prueba", "Categoria1", 10, 22.5);
        Producto p2 = new Producto(1L,"COD123", "Producto de prueba", "Categoria1", 10, 22.5);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
     @Test
    @DisplayName("toString - debe contener el nombre del producto en la representación")
    void toStringDebeContenerNombreDelProducto() {
        Producto producto = new Producto(1L,"COD123", "Producto de prueba", "Categoria1", 10, 22.5);
        String toString = producto.toString();
        assertTrue(toString.contains("Producto de prueba"));
        assertNotNull(toString);
    }

}
