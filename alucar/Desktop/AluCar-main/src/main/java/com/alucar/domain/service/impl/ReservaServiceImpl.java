package com.alucar.domain.service.impl;

import com.alucar.application.dto.request.ReservaRequestDTO;
import com.alucar.application.dto.response.ReservaResponseDTO;
import com.alucar.domain.model.*;
import com.alucar.domain.repository.*;
import com.alucar.domain.service.ReservaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarroRepository carroRepository;
    private final FilialRepository filialRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository,
                               CarroRepository carroRepository, FilialRepository filialRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.carroRepository = carroRepository;
        this.filialRepository = filialRepository;
    }

    @Override
    public ReservaResponseDTO criarReserva(String emailUsuario, ReservaRequestDTO dto) {
        if (dto.getDataFimPrevista().isBefore(dto.getDataInicioPrevista())) {
            throw new IllegalArgumentException("Data de fim não pode ser anterior à data de início.");
        }

        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Carro carro = carroRepository.findById(dto.getCarroId())
                .orElseThrow(() -> new RuntimeException("Carro não encontrado."));

        Filial filialRetirada = filialRepository.findById(dto.getFilialRetiradaId())
                .orElseThrow(() -> new RuntimeException("Filial de retirada não encontrada."));

        Filial filialDevolucao = filialRepository.findById(dto.getFilialDevolucaoId())
                .orElseThrow(() -> new RuntimeException("Filial de devolução não encontrada."));

        List<Reserva> conflitos = reservaRepository.findConflitos(
                carro.getId(), dto.getDataInicioPrevista(), dto.getDataFimPrevista());

        if (!conflitos.isEmpty()) {
            throw new IllegalStateException("Carro indisponível no período selecionado.");
        }

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setCarro(carro);
        reserva.setFilialRetirada(filialRetirada);
        reserva.setFilialDevolucao(filialDevolucao);
        reserva.setDataInicioPrevista(dto.getDataInicioPrevista());
        reserva.setDataFimPrevista(dto.getDataFimPrevista());

        Reserva salva = reservaRepository.save(reserva);
        return toDTO(salva);
    }

    @Override
    public List<ReservaResponseDTO> listarMinhasReservas(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        return reservaRepository.findByUsuarioId(usuario.getId())
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> listarTodas() {
        return reservaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public void cancelarReserva(String emailUsuario, Long idReserva, boolean isAdmin) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada."));

        if (!isAdmin && !reserva.getUsuario().getEmail().equals(emailUsuario)) {
            throw new SecurityException("Você não tem permissão para cancelar esta reserva.");
        }

        reserva.setStatus("cancelada");
        reservaRepository.save(reserva);
    }

    private ReservaResponseDTO toDTO(Reserva r) {
        return new ReservaResponseDTO(
                r.getIdReserva(),
                r.getCarro().getModelo(),
                r.getFilialRetirada().getNome(),
                r.getFilialDevolucao().getNome(),
                r.getDataReserva(),
                r.getDataInicioPrevista(),
                r.getDataFimPrevista(),
                r.getStatus()
        );
    }
}
