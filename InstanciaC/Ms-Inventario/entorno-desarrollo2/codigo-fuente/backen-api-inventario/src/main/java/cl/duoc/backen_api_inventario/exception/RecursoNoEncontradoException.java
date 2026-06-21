package cl.duoc.backen_api_inventario.exception;

public class RecursoNoEncontradoException  extends RuntimeException{
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
