package com.alucar.domain.service;

import com.alucar.application.dto.request.ReservaRequestDTO;
import com.alucar.application.dto.response.ReservaResponseDTO;

import java.util.List;

public interface ReservaService {
    ReservaResponseDTO criarReserva(String emailUsuario, ReservaRequestDTO dto);
    List<ReservaResponseDTO> listarMinhasReservas(String emailUsuario);
    List<ReservaResponseDTO> listarTodas();
    void cancelarReserva(String emailUsuario, Long idReserva, boolean isAdmin);
}
