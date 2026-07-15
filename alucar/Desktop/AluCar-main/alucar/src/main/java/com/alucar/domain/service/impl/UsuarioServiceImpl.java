package com.alucar.domain.service.impl;

import com.alucar.domain.model.Usuario;
import com.alucar.domain.repository.UsuarioRepository;
import com.alucar.domain.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario cadastroUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = buscarUsuarioPorId(id);
        usuario.setId(usuarioExistente.getId());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Page<Usuario> listarUsuarios(int pagina, int tamanho) {
        return usuarioRepository.findAll(PageRequest.of(pagina, tamanho));
    }

    @Override
    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorIdOptional(Long id) {
        return usuarioRepository.findById(id);
    }

    // Corrigido: Mantive apenas esta implementação de buscarPorEmail
    @Override
    public List<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                                .map(List::of)
                                .orElse(List.of());
    }

    @Override
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<Usuario> buscarPorTelefone(String telefone) {
        return usuarioRepository.findByTelefone(telefone);
    }

    @Override
    public List<Usuario> buscarTodUsuarios() {
        return usuarioRepository.findAll();
    }
}