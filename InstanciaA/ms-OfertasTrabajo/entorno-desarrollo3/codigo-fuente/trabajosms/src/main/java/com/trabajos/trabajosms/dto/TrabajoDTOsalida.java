package com.trabajos.trabajosms.dto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TrabajoDTOsalida {
    private Long id;
    private String cargo;
    private String jornada;
    private String modalidad;
    private String salario;
    private String descripcion;
}
