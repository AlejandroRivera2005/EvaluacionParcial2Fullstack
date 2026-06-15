package cl.duoc.backen_api_inventario.Dto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {
private Long id;
private String nombre;


private String categoria;


private Integer stock;


private double precio;

}
