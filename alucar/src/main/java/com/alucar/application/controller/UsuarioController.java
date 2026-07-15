package com.alucar.application.controller;

import com.alucar.domain.service.UsuarioService;
import com.alucar.domain.model.Usuario;
import com.alucar.application.dto.response.UsuarioResponseDTO;
import com.alucar.application.mapper.UsuarioMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {
    
    private final UsuarioMapper usuarioMapper;
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioMapper usuarioMapper, UsuarioService usuarioService) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuarios")
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioResponseDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        Usuario usuarioCriado = usuarioService.cadastroUsuario(usuario);
        UsuarioResponseDTO usuarioDTOCriado = usuarioMapper.toDTO(usuarioCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTOCriado);
    }

    @PutMapping("/usuarios/{id}")
    @Operation(summary = "Atualizar usuário existente")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioResponseDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
        UsuarioResponseDTO usuarioDTOAtualizado = usuarioMapper.toDTO(usuarioAtualizado);
        return ResponseEntity.ok(usuarioDTOAtualizado);
    }

    @GetMapping("/usuarios")
    @Operation(summary = "Listar todos os usuários")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.buscarTodUsuarios();
        List<UsuarioResponseDTO> usuarioDTOs = usuarios.stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarioDTOs);
    }

    @GetMapping("/usuarios/{id}")
    @Operation(summary = "Buscar usuário por ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        UsuarioResponseDTO usuarioDTO = usuarioMapper.toDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);   
    }

    @DeleteMapping("/usuarios/{id}")
    @Operation(summary = "Excluir usuário por ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por email")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);
        UsuarioResponseDTO usuarioDTO = usuarioMapper.toDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }
}
