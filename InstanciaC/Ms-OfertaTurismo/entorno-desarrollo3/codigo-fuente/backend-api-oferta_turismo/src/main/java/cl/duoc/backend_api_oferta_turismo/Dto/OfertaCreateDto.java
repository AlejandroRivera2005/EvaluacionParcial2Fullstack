package cl.duoc.backend_api_oferta_turismo.Dto;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OfertaCreateDto {

    @NotBlank(message = "El nombre de la oferta no puede estar nulo.")
    private String nombreOferta;

    @NotBlank(message = "La descripcion no puede estar vacia.")
    private String descripcion;

    @NotNull(message = "El precio no puede ser 0")
    @Min(value = 0, message = "El precio no puede ser negativo.")
    private double precio;
}
