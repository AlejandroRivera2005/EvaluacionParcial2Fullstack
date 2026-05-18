package cl.duoc.backen_api_inventario.Controller;
import cl.duoc.backen_api_inventario.Dto.ProductoCreateDto;
import cl.duoc.backen_api_inventario.Dto.ProductoDto;
import cl.duoc.backen_api_inventario.Service.ProductoService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List; 
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    
    @GetMapping
    public ResponseEntity<List<ProductoDto>> listarProductos() {
        return new ResponseEntity<>(productoService.obtenerTodosDto(), HttpStatus.OK);
    }

  
    @PostMapping("/registrar")
    public ResponseEntity<ProductoDto> registrarProducto(@Valid @RequestBody ProductoCreateDto dto) {
        ProductoDto guardado = productoService.registrarNuevoProducto(dto);
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

   
    @PutMapping("/actualizar/{codigo}")
    public ResponseEntity<?> actualizarProducto(@PathVariable String codigo, @Valid @RequestBody ProductoCreateDto dto) {
        ProductoDto actualizado = productoService.actualizarPorCodigo(codigo, dto);
        
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró el producto con código: " + codigo, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> eliminarProducto(@PathVariable String codigo) {
        boolean eliminado = productoService.eliminarPorCodigo(codigo);
        if (eliminado) {
            return new ResponseEntity<>("Producto eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }

}
