package cl.duoc.backend_api_oferta_turismo.Servicio;
import cl.duoc.backend_api_oferta_turismo.Dto.OfertaCreateDto;
import cl.duoc.backend_api_oferta_turismo.Dto.OfertaDto;
import cl.duoc.backend_api_oferta_turismo.Modelo.Oferta;
import cl.duoc.backend_api_oferta_turismo.Repositorio.OfertaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Service
public class OfertaService {


   @Autowired
    private OfertaRepository ofertaRepository;

    // Inyección por constructor (mantenida por consistencia con tus otros servicios)
    public OfertaService(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    public Optional<Oferta> findById(Long id) {
        return ofertaRepository.findById(id);
    }

    // Buscar y convertir a DTO
    public OfertaDto findDtoById(Long id) {
        return ofertaRepository.findById(id).map(oferta -> {
            return new OfertaDto(
                oferta.getId(),
                oferta.getNombreOferta(),
                oferta.getDescripcion(),
                oferta.getPrecio()
            );
        }).orElse(null);
    }

    // Registro de nueva oferta usando DTO
    public OfertaDto registrarNuevaOferta(OfertaCreateDto dto) {
        Oferta oferta = new Oferta();
        oferta.setNombreOferta(dto.getNombreOferta());
        oferta.setDescripcion(dto.getDescripcion());
        oferta.setPrecio(dto.getPrecio());

        Oferta guardada = ofertaRepository.save(oferta);

        return new OfertaDto(
            guardada.getId(),
            guardada.getNombreOferta(),
            guardada.getDescripcion(),
            guardada.getPrecio()
        );
    }

    
    public OfertaDto entidadADto(Oferta oferta) {
        OfertaDto dto = new OfertaDto();
        dto.setId(oferta.getId());
        dto.setNombreOferta(oferta.getNombreOferta());
        dto.setDescripcion(oferta.getDescripcion());
        dto.setPrecio(oferta.getPrecio());
        return dto;
    }

    public List<Oferta> obtenerTodas() {
        return ofertaRepository.findAll();
    }


    public List<String> validarOferta(Oferta oferta) {
        List<String> errores = new ArrayList<>();

        if (oferta.getNombreOferta() == null || oferta.getNombreOferta().trim().isEmpty()) {
            errores.add("El nombre de la oferta no puede estar nulo.");
        }

        if (oferta.getDescripcion() == null || oferta.getDescripcion().trim().isEmpty()) {
            errores.add("La descripcion no puede estar vacia.");
        }

        if (oferta.getPrecio() == 0) {
            errores.add("El precio no puede estar vacio.");
        } else if (oferta.getPrecio() < 0.1) {
            errores.add("El precio no puede ser negativo o menor a 0.1.");
        }

        return errores;
    }

    public Oferta guardarOferta(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    // Actualización por ID
    public Oferta actualizarPorId(Long id, Oferta nuevosDatos) {
        return ofertaRepository.findById(id).map(oferta -> {
            oferta.setNombreOferta(nuevosDatos.getNombreOferta());
            oferta.setDescripcion(nuevosDatos.getDescripcion());
            oferta.setPrecio(nuevosDatos.getPrecio());
            
            return ofertaRepository.save(oferta);
        }).orElse(null);
    }

    // Eliminación por ID
    public boolean eliminarPorId(Long id) {
        if (ofertaRepository.existsById(id)) {
            ofertaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
