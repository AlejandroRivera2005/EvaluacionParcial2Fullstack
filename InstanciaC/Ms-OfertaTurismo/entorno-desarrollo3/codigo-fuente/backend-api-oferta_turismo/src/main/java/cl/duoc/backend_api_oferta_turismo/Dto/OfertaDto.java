package cl.duoc.backend_api_oferta_turismo.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfertaDto {
 private Long id;
 private String nombreOferta;
 private String descripcion;
private double precio;
}
