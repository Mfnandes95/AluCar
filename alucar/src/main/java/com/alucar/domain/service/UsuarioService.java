package com.alucar.domain.service;

import com.alucar.domain.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {
    
    Usuario cadastroUsuario(Usuario usuario);
    Usuario buscarUsuarioPorId(Long id);
    Usuario buscarUsuarioPorEmail(String email);
    Usuario atualizarUsuario(Long id, Usuario usuario);
    void excluir(Long id);

    Page<Usuario> listarUsuarios(int pagina, int tamanho);
    void excluirUsuario(Long id);

    Optional<Usuario> buscarUsuarioPorIdOptional(Long id);

    List<Usuario> buscarPorEmail(String email);
    List<Usuario> buscarPorNome(String nome);
    List<Usuario> buscarPorTelefone(String telefone);
    List<Usuario> buscarTodUsuarios();
}