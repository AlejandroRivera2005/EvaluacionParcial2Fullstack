package com.microservicio.soportehotel.dto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoporteDTOsalida {
    private Long id;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String descripcion;

}