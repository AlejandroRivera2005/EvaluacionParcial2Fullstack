package cl.duoc.backend_api_oferta_turismo.Servicio;
import cl.duoc.backend_api_oferta_turismo.Dto.*;
import cl.duoc.backend_api_oferta_turismo.Exception.*;
import cl.duoc.backend_api_oferta_turismo.Repositorio.*;
import cl.duoc.backend_api_oferta_turismo.Modelo.*;
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
public class OfertaServiceTest {

       @Mock
    private OfertaRepository repository;

    @InjectMocks
    private OfertaService ofertaService;

     @Test
    @DisplayName("findAll - debe retornar lista de ofertas cuando existen registros")
    void debeRetornarListaDeOfertas () {
        // Given
        Oferta oferta1 = new Oferta(1L,"Oferta 1", "Descripción 1", 50.0);
        Oferta oferta2 = new Oferta(2L,"Oferta 2", "Descripción 2", 150.0);
        when(repository.findAll()).thenReturn(List.of(oferta1, oferta2));

        // When
        List<Oferta> ofertas = repository.findAll();

        // Then
        assertEquals(2, ofertas.size());
        assertEquals("Oferta 1", ofertas.get(0).getNombreOferta());
        assertEquals("Oferta 2", ofertas.get(1).getNombreOferta());
        assertNotNull(ofertas);
        verify(repository, times(1)).findAll();
    }
        @Test
    @DisplayName("findAll - debe retornar lista vacía cuando no hay ofertas")
    void debeRetornarListaVaciaSiNoHayProductos() {
        // Given
        when(repository.findAll()).thenReturn(List.of());
        List<OfertaDto> ofertas = ofertaService.findAll();
        assertNotNull(ofertas);
        assertTrue(ofertas.isEmpty());}
         @Test
    @DisplayName("findById - debe retornar el DTO correcto cuando la oferta existe")
    void debeRetornarOfertaPorId() {
        // Given
        Oferta oferta = new Oferta(1L,"Oferta de prueba", "Descripción de prueba", 100.0);
        when(repository.findById(1L)).thenReturn(Optional.of(oferta));
        OfertaDto ofertaDto = ofertaService.findDtoById(1L);
        assertNotNull(ofertaDto);
        assertEquals("Oferta de prueba", ofertaDto.getNombreOferta());
        assertEquals("Descripción de prueba", ofertaDto.getDescripcion());
        assertEquals(100.0, ofertaDto.getPrecio());
        assertEquals(1L, ofertaDto.getId());}
        
    @Test
    @DisplayName("findById - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoProductoNoExiste() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());
        // When & Then
         assertThrows(RecursoNoEncontradoException.class, () ->
            ofertaService.findById(999L)
        );}
         @Test
    @DisplayName("crear - debe persistir y retornar la oferta con ID generado")
    void debeCrearOfertaCorrectamente() {
        // Given
        OfertaCreateDto dto = new OfertaCreateDto();
        dto.setNombreOferta("Nueva oferta");
        dto.setDescripcion("Nueva descripción");
        dto.setPrecio(200.0);

        Oferta ofertaGuardada = new Oferta(1L,"Nueva oferta", "Nueva descripción", 200.0);
        when(repository.save(any(Oferta.class))).thenReturn(ofertaGuardada);

        // When
        OfertaDto resultado = ofertaService.registrarNuevaOferta(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Nueva oferta", resultado.getNombreOferta());
        assertEquals("Nueva descripción", resultado.getDescripcion());
        assertEquals(200.0, resultado.getPrecio());
        verify(repository, times(1)).save(any(Oferta.class));
    }
    
    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un ID inexistente")
    void debeLanzarExcepcionAlEliminarOfertaInexistente() {
        // Given
        when(repository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            ofertaService.eliminarPorId(999L)
        );
        verify(repository, never()).deleteById(any());
    }
     @Test
    @DisplayName("eliminar - debe invocar deleteById cuando la oferta existe")
    void debeEliminarOfertaExistente() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);

        // When
        ofertaService.eliminarPorId(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }
}