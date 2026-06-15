package com.trabajos.trabajosms.modelo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TrabajoModeloTest {
    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void debeCrearUnaInstanciaNoNula() {
        TrabajoModelo trabajo = new TrabajoModelo();
        assertNotNull(trabajo);
    }

    @Test
    @DisplayName("Constructor con parámetros - debe asignar correctamente los valores")
    void debeAsignarCorrectamenteLosValores() {
        TrabajoModelo trabajo = new TrabajoModelo( null, "Trabajo de ejemplo", "Full-time", "Presencial", "100.00", "Descripción del trabajo");
        assertNotNull(trabajo);
        assertEquals(1L, trabajo.getId());
        assertEquals("Trabajo de ejemplo", trabajo.getCargo());
        assertEquals("Full-time", trabajo.getJornada());
        assertEquals("Presencial", trabajo.getModalidad());
        assertEquals(new BigDecimal("100.00"), trabajo.getSalario());
        assertEquals("Descripción del trabajo", trabajo.getDescripcion());
    }

    @Test
    @DisplayName("Setters - debe permitir modificar los valores de los atributos")
    void debePermitirModificarLosValoresDeLosAtributos() {
        TrabajoModelo trabajo = new TrabajoModelo();
        trabajo.setId(1L);
        trabajo.setCargo("Trabajo de ejemplo");
        trabajo.setJornada("Full-time");
        trabajo.setModalidad("Presencial");
        trabajo.setSalario("100.00");
        trabajo.setDescripcion("Descripción del trabajo");

        assertEquals(1L, trabajo.getId());
        assertEquals("Trabajo de ejemplo", trabajo.getCargo());
        assertEquals("Full-time", trabajo.getJornada());
        assertEquals("Presencial", trabajo.getModalidad());
        assertEquals(new BigDecimal("100.00"), trabajo.getSalario());
        assertEquals("Descripción del trabajo", trabajo.getDescripcion());
    }

    @Test
    @DisplayName("equals y hashCode - debe considerar dos objetos iguales si tienen el mismo id")
    void debeConsiderarDosObjetosIgualesSiTienenElMismoId() {
        TrabajoModelo trabajo1 = new TrabajoModelo(1L, "Trabajo de ejemplo", "Full-time", "Presencial", "100.00", "Descripción del trabajo");
        TrabajoModelo trabajo2 = new TrabajoModelo(1L, "Trabajo de ejemplo", "Full-time", "Presencial", "100.00", "Descripción del trabajo");

        assertEquals(trabajo1, trabajo2);
        assertEquals(trabajo1.hashCode(), trabajo2.hashCode());
    }


    @Test
    @DisplayName("toString - debe devolver una representación del objeto")
    void debeDevolverUnaRepresentacionDelObjeto() {
        TrabajoModelo trabajo = new TrabajoModelo(1L, "Trabajo de ejemplo", "Full-time", "Presencial", "100.00", "Descripción del trabajo");
        String texto = trabajo.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("TrabajoModelo"));
    }

}
