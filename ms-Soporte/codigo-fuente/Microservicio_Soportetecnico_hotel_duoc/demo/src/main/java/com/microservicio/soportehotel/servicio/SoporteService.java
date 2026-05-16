package com.microservicio.soportehotel.servicio;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.microservicio.soportehotel.dto.SoporteDTOentrada;
import com.microservicio.soportehotel.dto.SoporteDTOsalida;
import com.microservicio.soportehotel.repositorio.SoporteRepository;
import com.microservicio.soportehotel.soporte_modelo.Soporte; 
import java.util.List;
import java.util.Optional;
@Service

public class SoporteService {
    private final SoporteRepository soporteRepository;

    public SoporteService(SoporteRepository soporteRepository) {
        this.soporteRepository = soporteRepository;
    }

    public SoporteDTOsalida findDtoById(@NonNull Long id) {
        Soporte s = soporteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con id: " + id));

        return new SoporteDTOsalida(
            s.getId(),
            s.getNombreCompleto(),
            s.getCorreo(),
            s.getTelefono(),
            s.getDescripcion()
        );
    }

    public List<SoporteDTOsalida> findAllDTOsalida() {
        List<Soporte> soportes = soporteRepository.findAll();
        return soportes.stream()
            .map(s -> new SoporteDTOsalida(
                s.getId(),
                s.getNombreCompleto(),
                s.getCorreo(),
                s.getTelefono(),
                s.getDescripcion()
            ))
            .toList();
    }

    public SoporteDTOsalida crearSoporte(SoporteDTOentrada dto) {

    Soporte s = new Soporte();
    s.setNombreCompleto(dto.getNombreCompleto());
    s.setCorreo(dto.getCorreo());
    s.setTelefono(dto.getTelefono());
    s.setDescripcion(dto.getDescripcion());
     Soporte guardado = soporteRepository.save(s);

  
    return new SoporteDTOsalida(
            guardado.getId(),
            guardado.getNombreCompleto(),
            guardado.getCorreo(),
            guardado.getTelefono(),
            guardado.getDescripcion()
        );
    }

    public SoporteDTOsalida actualizarDTOsolicitud(@NonNull Long id, SoporteDTOentrada dto) {
    Optional<Soporte> soporteExistente = soporteRepository.findById(id);
    
    if (soporteExistente.isPresent()) {
        Soporte soporteModificado = soporteExistente.get();
        
        soporteModificado.setNombreCompleto(dto.getNombreCompleto());
        soporteModificado.setCorreo(dto.getCorreo());
        soporteModificado.setTelefono(dto.getTelefono());
        soporteModificado.setDescripcion(dto.getDescripcion());
        
        Soporte actualizado = soporteRepository.save(soporteModificado);
        
        return new SoporteDTOsalida(
            actualizado.getId(),
            actualizado.getNombreCompleto(),
            actualizado.getCorreo(),
            actualizado.getTelefono(),
            actualizado.getDescripcion()
        );
        } else {
            throw new RuntimeException("Solicitud no encontrada con id: " + id);
        }
    }
    

    public boolean eliminarSolicitud(@NonNull Long id) {
        if (soporteRepository.existsById(id)){
            soporteRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Solicitud no encontrada con id: " + id);
        }
    }


}
