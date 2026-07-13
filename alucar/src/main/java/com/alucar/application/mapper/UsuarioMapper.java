package com.alucar.application.mapper;

import com.alucar.domain.model.Usuario;
import com.alucar.application.dto.response.UsuarioResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    
    public Usuario toEntity(UsuarioResponseDTO usuarioDTO) {
        if (usuarioDTO == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setEndereco(usuarioDTO.getEndereco());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setCnh(usuarioDTO.getCnh());
        return usuario;
    }

    public UsuarioResponseDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .telefone(usuario.getTelefone())
                .endereco(usuario.getEndereco())
                .cpf(usuario.getCpf())
                .cnh(usuario.getCnh())
                .build();
    }
}
