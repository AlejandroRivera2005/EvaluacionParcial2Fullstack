package com.pagos.pagosms.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pagos.pagosms.modelo.PagoModelo;

public interface PagoRepositorio extends JpaRepository<PagoModelo, Long> {
}