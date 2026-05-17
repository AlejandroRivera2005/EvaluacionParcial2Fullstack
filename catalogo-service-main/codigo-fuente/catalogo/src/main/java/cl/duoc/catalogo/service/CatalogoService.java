package cl.duoc.catalogo.service;

import cl.duoc.catalogo.model.Catalogo;
import cl.duoc.catalogo.repository.CatalogoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Service indica que esta clase contiene la lógica de negocio.
 */
@Service
public class CatalogoService {

    // Se declara el repositorio para poder usar las funciones de la base de datos.
    private final CatalogoRepository libroRepository;

    // Constructor para inyectar la dependencia del repositorio.
    public CatalogoService(CatalogoRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    /**
     * CREATE (Crear): Guarda un nuevo libro en la base de datos.
     */
    public Catalogo guardarLibro(Catalogo libro) {
        // El método save() es provisto automáticamente por JpaRepository
        return libroRepository.save(libro);
    }

    /**
     * READ (Leer todos): Obtiene una lista con todos los libros guardados.
     */
    public List<Catalogo> obtenerTodos() {
        // El método findAll() trae todos los registros de la tabla
        return libroRepository.findAll();
    }

    /**
     * READ (Leer por ID): Busca un libro específico usando su identificador.
     * Devuelve un Optional porque el libro podría no existir.
     */
    public Optional<Catalogo> obtenerPorId(Long id) {
        return libroRepository.findById(id);
    }

    /**
     * UPDATE (Actualizar): Reemplaza los datos de un libro existente.
     */
    public Catalogo actualizarLibro(Long id, Catalogo detallesLibro) {
        // Primero verificamos si el libro existe en la base de datos
        Optional<Catalogo> libroExistente = libroRepository.findById(id);
        
        if (libroExistente.isPresent()) {
            // Obtenemos el libro real de la base de datos
            Catalogo libroAActualizar = libroExistente.get();
            // Actualizamos sus datos con los nuevos datos recibidos
            libroAActualizar.setNumeroHabitacion(detallesLibro.getNumeroHabitacion());
            libroAActualizar.setTipoHabitacion(detallesLibro.getTipoHabitacion());
            libroAActualizar.setServiciosHabitacion(detallesLibro.getServiciosHabitacion());
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