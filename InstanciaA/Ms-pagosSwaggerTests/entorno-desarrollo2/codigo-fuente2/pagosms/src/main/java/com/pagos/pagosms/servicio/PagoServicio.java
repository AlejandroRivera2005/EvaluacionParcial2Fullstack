package com.pagos.pagosms.servicio;
import com.pagos.pagosms.excepcion.RecursoNoEncontradoException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.pagos.pagosms.repositorio.PagoRepositorio;
import com.pagos.pagosms.modelo.PagoModelo;
import com.pagos.pagosms.dto.PagoDTOsalida;
import com.pagos.pagosms.dto.PagoDTOentrada;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PagoServicio {
    private final PagoRepositorio pagoRepositorio;

    public PagoServicio(PagoRepositorio pagoRepositorio) {
        this.pagoRepositorio = pagoRepositorio;
    }
    
    public PagoDTOsalida crearPago(PagoDTOentrada dto) {

        PagoModelo s = new PagoModelo();
        s.setMetodo_pago(dto.getMetodo_pago());
        s.setNumero_tarjeta(dto.getNumero_tarjeta());
        s.setFecha_vencimiento(dto.getFecha_vencimiento());
        s.setCvv(dto.getCvv());
    
        PagoModelo guardado = pagoRepositorio.save(s);
        return new PagoDTOsalida(guardado);
    }

    
    public PagoDTOsalida findDtoById(Long id) {
    PagoModelo p = pagoRepositorio.findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("El método de pago no existe"));

    return new PagoDTOsalida(p); 
    }

    public List<PagoDTOsalida> findAll() {
        List<PagoModelo> pagos = pagoRepositorio.findAll();
        return pagos.stream().map(PagoDTOsalida::new).toList();
    }

    public PagoDTOsalida actualizarPago(Long id, PagoDTOentrada dto){
        PagoModelo p = pagoRepositorio.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("El método de pago no existe"));

        p.setMetodo_pago(dto.getMetodo_pago());
        p.setNumero_tarjeta(dto.getNumero_tarjeta());
        p.setFecha_vencimiento(dto.getFecha_vencimiento());
        p.setCvv(dto.getCvv());

        PagoModelo actualizado = pagoRepositorio.save(p);
        return new PagoDTOsalida(actualizado);

    }

    public boolean eliminarPago(Long id) {
        if (pagoRepositorio.existsById(id)) {
            pagoRepositorio.deleteById(id);
            return true;
        } else {
            throw new RecursoNoEncontradoException("El método de pago no existe");
        }
    }
   

}
