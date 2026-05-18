package cl.duoc.backen_api_inventario.Dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCreateDto {
@NotBlank(message = "El codigo no puede estar vacio.")
private String codigo;

@NotBlank(message = "El nombre no puede estar vacio.")
private String nombre;

@NotBlank(message = "La categoria del producto no puede estar vacia.")
private String categoria;

@NotNull(message = "El stock no puede estar vacio.")
@Min(value = 0, message = "El stock no puede ser negativo.")
private Integer stock;

@NotNull(message = "El precio no puede estar vacio.")
@DecimalMin(value = "0.1" , message = "El precio no puede ser menor a 0,1.")
private double precio;
}
