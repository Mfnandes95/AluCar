package com.alucar.application.controller;

import com.alucar.application.dto.request.ReservaRequestDTO;
import com.alucar.application.dto.response.ReservaResponseDTO;
import com.alucar.domain.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> criarReserva(@RequestBody ReservaRequestDTO dto) {
        String email = emailDoUsuarioLogado();
        ReservaResponseDTO reserva = reservaService.criarReserva(email, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<ReservaResponseDTO>> listarMinhasReservas() {
        String email = emailDoUsuarioLogado();
        return ResponseEntity.ok(reservaService.listarMinhasReservas(email));
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        String email = emailDoUsuarioLogado();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        reservaService.cancelarReserva(email, id, isAdmin);
        return ResponseEntity.noContent().build();
    }

    private String emailDoUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName(); // no seu UserDetailsServiceImpl, o "username" é o email
    }
}
