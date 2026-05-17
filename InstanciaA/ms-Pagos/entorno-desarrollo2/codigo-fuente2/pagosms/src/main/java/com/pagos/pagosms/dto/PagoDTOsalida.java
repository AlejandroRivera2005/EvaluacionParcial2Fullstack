package com.pagos.pagosms.dto;
import lombok.Data;

import com.pagos.pagosms.modelo.PagoModelo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTOsalida {

    private Long id;
    private String metodo_pago;
    private String tarjeta_enmascarada;

    
    public PagoDTOsalida(PagoModelo p) {

        this.id = p.getId();
        this.metodo_pago = p.getMetodo_pago();
        String numeroTarjeta = p.getNumero_tarjeta();
        if (numeroTarjeta != null && numeroTarjeta.length() >= 4) {
            String ultimosCuatro = numeroTarjeta.substring(numeroTarjeta.length() - 4);
            this.tarjeta_enmascarada = "**** **** **** " + ultimosCuatro;
        } else {
            this.tarjeta_enmascarada = "**** **** **** ****";
        }
    }
}
