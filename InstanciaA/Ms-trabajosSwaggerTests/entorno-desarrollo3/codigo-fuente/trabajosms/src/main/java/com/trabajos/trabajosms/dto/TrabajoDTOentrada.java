package com.trabajos.trabajosms.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrabajoDTOentrada {
    @NotBlank(message = "Indica el nombre del cargo")
    @Size(max = 100, message = "El cargo no puede tener más de 100 caracteres")
    private String cargo;

    @NotBlank(message = "Indica la jornada laboral del cargo")
    @Size(max = 50, message = "La jornada laboral no puede tener más de 50 caracteres")
    private String jornada;

    @NotBlank(message = "Indica la modalidad del cargo")
    @Size(max = 50, message = "La modalidad no puede tener más de 50 caracteres")
    private String modalidad;   

    @NotBlank(message = "Indica el salario del cargo")
    @Size(max = 50, message = "El salario no puede tener más de 50 caracteres")
    private String salario;

    @NotBlank(message = "Indica la descripción del cargo")
    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
    private String descripcion;

}
