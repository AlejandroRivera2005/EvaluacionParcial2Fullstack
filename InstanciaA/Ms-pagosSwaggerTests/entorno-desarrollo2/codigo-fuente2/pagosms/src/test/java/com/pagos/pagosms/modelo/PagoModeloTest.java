package com.pagos.pagosms.modelo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



class PagoModeloTest {
    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        PagoModelo pago = new PagoModelo();
        assertNotNull(pago);
    }

    @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        PagoModelo pago = new PagoModelo(
            null, "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123"
        );

        assertEquals(null, pago.getId());
        assertEquals("Tarjeta de Crédito", pago.getMetodo_pago());
        assertEquals("1234 5678 9012 3456", pago.getNumero_tarjeta());
        assertEquals("12/25", pago.getFecha_vencimiento());
        assertEquals("123", pago.getCvv());
    }

    @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {
        PagoModelo pago = new PagoModelo();

        pago.setId(2L);
        pago.setMetodo_pago("Tarjeta de Débito");
        pago.setNumero_tarjeta("9876 5432 1098 7654");
        pago.setFecha_vencimiento("12/26");
        pago.setCvv("456");

        assertEquals(2L, pago.getId());
        assertEquals("Tarjeta de Débito", pago.getMetodo_pago());
        assertEquals("9876 5432 1098 7654", pago.getNumero_tarjeta());
        assertEquals("12/26", pago.getFecha_vencimiento());
        assertEquals("456", pago.getCvv());
    }

    @Test
    @DisplayName("equals y hashCode - dos pagos con los mismos datos deben ser iguales")
    void dosPagosConMismosDatosDebenSerIguales() {
        PagoModelo p1 = new PagoModelo(
            null, "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123"
        );
        PagoModelo p2 = new PagoModelo(
            null, "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123"
        );

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    @DisplayName("toString - debe contener el nombre del producto en la representación")
    void toStringDebeContenerNombreDelProducto() {
        PagoModelo pago = new PagoModelo(
            null, "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123"
        );

        String texto = pago.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("Tarjeta de Crédito"));
    }
    

}
