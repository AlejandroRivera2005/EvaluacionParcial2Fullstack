// src/main/java/com/duoc/pacientes/dto/PacienteCreateDTO.java
/**

package cl.duoc.demo.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String mensaje;

    @NotBlank(message = "El RUT es obligatorio")
    private String fecha;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String hora;

    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private String categoria;
}
    
*/