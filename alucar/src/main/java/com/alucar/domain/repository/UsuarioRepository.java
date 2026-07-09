package com.alucar.domain.repository;

import com.alucar.domain.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(Long id);
}