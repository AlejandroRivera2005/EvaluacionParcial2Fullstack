package com.microservicio.soportehotel.modelo;

import com.microservicio.soportehotel.soporte_modelo.Soporte;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class SoporteModeloTest {

     @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        Soporte soporte = new Soporte();
        assertNotNull(soporte);
    }

    @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        Soporte soporte = new Soporte(
            1L, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web"
        );

        assertEquals(1L, soporte.getId());
        assertEquals("Juan Pérez", soporte.getNombreCompleto());
        assertEquals("juanperez1@gmail.com", soporte.getCorreo());
        assertEquals("123456789", soporte.getTelefono());
        assertEquals("Problema con la página web", soporte.getDescripcion());
    }

    @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {
        Soporte soporte = new Soporte();

        soporte.setId(2L);
        soporte.setNombreCompleto("Juan Pérez");
        soporte.setCorreo("juanperez1@gmail.com");
        soporte.setTelefono("123456789");
        soporte.setDescripcion("Problema con la página web");

        
        assertEquals(2L, soporte.getId());
        assertEquals("Juan Pérez", soporte.getNombreCompleto());
        assertEquals("juanperez1@gmail.com", soporte.getCorreo());
        assertEquals("123456789", soporte.getTelefono());
        assertEquals("Problema con la página web", soporte.getDescripcion());
    }

     @Test
    @DisplayName("equals y hashCode - dos productos con los mismos datos deben ser iguales")
    void dosProductosConMismosDatosDebenSerIguales() {
        Soporte p1 = new Soporte(1L, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web");
        Soporte p2 = new Soporte(1L, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    @DisplayName("toString - debe contener el nombre del producto en la representación")
    void toStringDebeContenerNombreDelProducto() {
        Soporte soporte = new Soporte(3L, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web");

        String texto = soporte.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("Juan Pérez"));
    }
}

 


