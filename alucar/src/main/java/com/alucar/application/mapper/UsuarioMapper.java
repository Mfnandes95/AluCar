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
        if (usuario == null) {
            return null;
        }
        UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setTelefone(usuario.getTelefone());
        usuarioDTO.setEndereco(usuario.getEndereco());
        usuarioDTO.setCpf(usuario.getCpf());
        usuarioDTO.setCnh(usuario.getCnh());
        return usuarioDTO;
    }
}
