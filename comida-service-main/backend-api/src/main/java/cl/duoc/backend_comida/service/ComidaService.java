package cl.duoc.backend_comida.service;

import org.springframework.stereotype.Service;

import cl.duoc.backend_comida.model.Comida;
import cl.duoc.backend_comida.repository.ComidaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Service indica que esta clase contiene la lógica de negocio.
 */
@Service
public class ComidaService {

    // Se declara el repositorio para poder usar las funciones de la base de datos.
    private final ComidaRepository libroRepository;

    // Constructor para inyectar la dependencia del repositorio.
    public ComidaService(ComidaRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    /**
     * CREATE (Crear): Guarda un nuevo libro en la base de datos.
     */
    public Comida guardarLibro(Comida libro) {
        // El método save() es provisto automáticamente por JpaRepository
        return libroRepository.save(libro);
    }

    /**
     * READ (Leer todos): Obtiene una lista con todos los libros guardados.
     */
    public List<Comida> obtenerTodos() {
        // El método findAll() trae todos los registros de la tabla
        return libroRepository.findAll();
    }

    /**
     * READ (Leer por ID): Busca un libro específico usando su identificador.
     * Devuelve un Optional porque el libro podría no existir.
     */
    public Optional<Comida> obtenerPorId(Long id) {
        return libroRepository.findById(id);
    }

    /**
     * UPDATE (Actualizar): Reemplaza los datos de un libro existente.
     */
    public Comida actualizarLibro(Long id, Comida detallesLibro) {
        // Primero verificamos si el libro existe en la base de datos
        Optional<Comida> libroExistente = libroRepository.findById(id);
        
        if (libroExistente.isPresent()) {
            // Obtenemos el libro real de la base de datos
            Comida libroAActualizar = libroExistente.get();
            // Actualizamos sus datos con los nuevos datos recibidos
            libroAActualizar.setNombrePlato(detallesLibro.getNombrePlato());
            libroAActualizar.setContieneCarne(detallesLibro.getContieneCarne());
            libroAActualizar.setCodigo(detallesLibro.getCodigo());
            libroAActualizar.setPrecio(detallesLibro.getPrecio());
            // Guardamos los cambios
            return libroRepository.save(libroAActualizar);
        } else {
            // Si no existe, retornamos nulo o podríamos lanzar un error
            return null; 
        }
    }

    /**
     * DELETE (Eliminar): Borra un libro de la base de datos usando su ID.
     */
    public boolean eliminarLibro(Long id) {
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}