package com.pagos.pagosms.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PagoDTOentrada {
    @NotBlank(message= "Indica tu método de pago")
    private String metodo_pago;

    @NotBlank(message= "El número de tarjeta es obligatorio")
    @Size(min = 19, max = 19, message = "Ingresa un número de tarjeta válido (16 dígitos más espacios)")
    private String numero_tarjeta;

    @NotBlank(message= "La fecha de vencimiento es obligatoria")
    @Size(min = 5, max = 5, message = "Ingresa una fecha de vencimiento válida (MM/AA)")
    private String fecha_vencimiento;

    @NotBlank(message = "El CVV es obligatorio")
    @Size(min = 3, max = 4, message = "Ingresa un CVV válido (3 o 4 dígitos)")
    private String cvv;

}
