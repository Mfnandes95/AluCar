package com.alucar.domain.repository;

import com.alucar.domain.model.Reserva;
import java.util.List;

public interface ReservaRepository {
    List<Reserva> findByUsuarioId(String usuarioId);
    List<Reserva> findByVeiculoId(String veiculoId);
}
