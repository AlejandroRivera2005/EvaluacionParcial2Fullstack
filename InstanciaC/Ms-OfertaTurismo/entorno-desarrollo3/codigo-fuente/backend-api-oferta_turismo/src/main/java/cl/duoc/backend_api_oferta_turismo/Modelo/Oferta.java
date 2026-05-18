package cl.duoc.backend_api_oferta_turismo.Modelo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity

public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la oferta no puede estar nulo.")
    private String nombreOferta;

    @NotBlank(message = "La descripcion no puede estar vacia.")
    private String descripcion;

    @NotNull(message = "El precio no puede ser 0")
    @Min(value = 0, message = "El precio no puede ser negativo.")
    private double precio;
}
