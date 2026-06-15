package cl.duoc.backen_api_inventario.Service;
import cl.duoc.backen_api_inventario.Dto.ProductoCreateDto;
import cl.duoc.backen_api_inventario.Dto.ProductoDto;
import cl.duoc.backen_api_inventario.Model.Producto;
import cl.duoc.backen_api_inventario.Repository.ProductoRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // 1. REGISTRAR (Usa CreateDTO -> Retorna DTO)
    public ProductoDto registrarNuevoProducto(ProductoCreateDto dto) {
        Producto producto = new Producto();
        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setStock(dto.getStock());
        producto.setPrecio(dto.getPrecio());

        Producto guardado = productoRepository.save(producto);
        return entidadADto(guardado);
    }

    // 2. BUSCAR POR CÓDIGO (Retorna DTO)
    public ProductoDto findDtoByCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo)
                .map(this::entidadADto)
                .orElse(null);
    }

    // 3. OBTENER TODOS (Retorna lista de DTOs)
    public List<ProductoDto> obtenerTodosDto() {
        return productoRepository.findAll().stream()
                .map(this::entidadADto)
                .collect(Collectors.toList());
    }

    // 4. ACTUALIZAR (Usa CreateDTO -> Retorna DTO)
    public ProductoDto actualizarPorCodigo(String codigo, ProductoCreateDto nuevosDatos) {
        return productoRepository.findByCodigo(codigo).map(producto -> {
            producto.setNombre(nuevosDatos.getNombre());
            producto.setCategoria(nuevosDatos.getCategoria());
            producto.setStock(nuevosDatos.getStock());
            producto.setPrecio(nuevosDatos.getPrecio());
            
            Producto actualizado = productoRepository.save(producto);
            return entidadADto(actualizado);
        }).orElse(null);
    }

    // 5. ELIMINAR POR CÓDIGO (¡Aquí está!)
    public boolean eliminarPorCodigo(String codigo) {
        // Usamos el método que creaste en el Repository
        if (productoRepository.existsByCodigoIgnoreCase(codigo)) {
            productoRepository.deleteByCodigoIgnoreCase(codigo);
            return true;
        }
        return false;
    }

    // MÉTODO AUXILIAR DE CONVERSIÓN
    public ProductoDto entidadADto(Producto producto) {
        ProductoDto dto = new ProductoDto();
        dto.setNombre(producto.getNombre());
        dto.setCategoria(producto.getCategoria());
        dto.setStock(producto.getStock());
        dto.setPrecio(producto.getPrecio());
        return dto;
    }
    public List<String> validarProducto(Producto producto) {
        List<String> errores = new ArrayList<>();
        if (producto.getCodigo() == null || producto.getCodigo().trim().isEmpty()) {
            errores.add("El código no puede estar vacío.");
        }
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            errores.add("El nombre no puede estar vacío.");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            errores.add("El stock no puede ser negativo.");
        }
        if (producto.getPrecio() < 0.1) {
            errores.add("El precio debe ser mayor a 0.1.");
        }
        return errores;
    }
    
}
