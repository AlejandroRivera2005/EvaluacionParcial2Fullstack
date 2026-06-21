package cl.duoc.backen_api_inventario.Service;
import cl.duoc.backen_api_inventario.Model.Producto;
import cl.duoc.backen_api_inventario.Repository.ProductoRepository;
import cl.duoc.backen_api_inventario.exception.RecursoNoEncontradoException;
import cl.duoc.backen_api_inventario.Dto.ProductoDto;
import cl.duoc.backen_api_inventario.Dto.ProductoCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    @DisplayName("findAll - debe retornar lista de productos cuando existen registros")
    void debeRetornarListaDeProductos() {
        // Given
        List<Producto> productosSimulados = List.of(
                new Producto(1L,"COD123", "Producto A", "Categoria1", 10, 22.5),
                new Producto(2L,"COD456", "Producto B", "Categoria2", 20, 45.0)
        );
        when(repository.findAll()).thenReturn(productosSimulados);
         List<ProductoDto> resultado = productoService.findAll();
        
        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(2L, resultado.get(1).getId());
        verify(repository, times(1)).findAll();}
        
    @Test
    @DisplayName("findAll - debe retornar lista vacía cuando no hay productos")
    void debeRetornarListaVaciaSiNoHayProductos() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<ProductoDto> resultado = productoService.findAll();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
     @Test
    @DisplayName("findById - debe retornar el DTO correcto cuando el producto existe")
    void debeRetornarProductoPorId() {
        // Given
        Producto productoSimulado = new Producto(1L,"COD123", "Producto A", "Categoria1", 10, 22.5);
        when(repository.findById(1L)).thenReturn(Optional.of(productoSimulado));
         // When
        ProductoDto resultado = productoService.findById(1L);
        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Producto A", resultado.getNombre());
        assertEquals("Categoria1", resultado.getCategoria());
        assertEquals(10, resultado.getStock());
        assertEquals(22.5, resultado.getPrecio(), 0.01);
    }
     @Test
    @DisplayName("findById - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoProductoNoExiste() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            productoService.findById(999L)
        );
    }
        @Test
    @DisplayName("crear - debe persistir y retornar el producto con ID generado")
    void debeCrearProductoCorrectamente() {
        // Given
        ProductoCreateDto nuevoProducto = new ProductoCreateDto("COD789", "Producto C", "Categoria3", 30, 60.0);
        Producto productoGuardado = new Producto(3L,"COD789", "Producto C", "Categoria3", 30, 60.0);
        when(repository.save(any(Producto.class))).thenReturn(productoGuardado);
        // When
        ProductoDto resultado = productoService.registrarNuevoProducto(nuevoProducto);
        // Then
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Producto C", resultado.getNombre());
        assertEquals("Categoria3", resultado.getCategoria());
        assertEquals(30, resultado.getStock());
        assertEquals(60.0, resultado.getPrecio(), 0.01);
        verify(repository, times(1)).save(any(Producto.class));
    }
    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un código inexistente")
    void debeLanzarExcepcionAlEliminarProductoInexistente() {
        // Given
        when(repository.existsByCodigoIgnoreCase("COD999")).thenReturn(false);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            productoService.eliminarPorCodigo("COD999")
        );
        verify(repository, never()).deleteByCodigoIgnoreCase(any());
    }
     @Test
    @DisplayName("eliminar - debe invocar deleteByCodigoIgnoreCase cuando el producto existe")
    void debeEliminarProductoExistente() {
        // Given
        when(repository.existsByCodigoIgnoreCase("COD123")).thenReturn(true);

        // When
        productoService.eliminarPorCodigo("COD123");

        // Then
        verify(repository, times(1)).deleteByCodigoIgnoreCase("COD123");
    }
}
