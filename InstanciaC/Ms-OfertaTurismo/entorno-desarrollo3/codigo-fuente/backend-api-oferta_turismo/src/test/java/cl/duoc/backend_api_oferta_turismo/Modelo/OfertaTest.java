package cl.duoc.backend_api_oferta_turismo.Modelo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;
public class OfertaTest {

        @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        Oferta oferta = new Oferta();
        assertNotNull(oferta);
    }

     @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        Oferta oferta = new Oferta(1L,"Oferta de prueba", "Descripción de prueba", 100.0);
        assertEquals(1L, oferta.getId());
        assertEquals("Oferta de prueba", oferta.getNombreOferta());
        assertEquals("Descripción de prueba", oferta.getDescripcion());
        assertEquals(100.0, oferta.getPrecio());
    }

    @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {
        Oferta oferta = new Oferta();
        oferta.setId(2L);
        oferta.setNombreOferta("Nueva oferta");
        oferta.setDescripcion("Nueva descripción");
        oferta.setPrecio(200.0);

        assertEquals(2L, oferta.getId());
        assertEquals("Nueva oferta", oferta.getNombreOferta());
        assertEquals("Nueva descripción", oferta.getDescripcion());
        assertEquals(200.0, oferta.getPrecio());
    }
    
    @Test
    @DisplayName("equals y hashCode - dos productos con los mismos datos deben ser iguales")
    void dosProductosConMismosDatosDebenSerIguales() {
        Oferta oferta1 = new Oferta(1L,"Oferta de prueba", "Descripción de prueba", 100.0);
        Oferta oferta2 = new Oferta(1L,"Oferta de prueba", "Descripción de prueba", 100.0);

        assertEquals(oferta1, oferta2);
        assertEquals(oferta1.hashCode(), oferta2.hashCode());
    }

    
    @Test
    @DisplayName("toString - debe contener el nombre del producto en la representación")
    void toStringDebeContenerNombreDelProducto() {
        Oferta oferta = new Oferta(1L,"Oferta de prueba", "Descripción de prueba", 100.0);
        String toString = oferta.toString();
        assertTrue(toString.contains("Oferta de prueba"));
    }
}
