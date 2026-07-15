package com.alucar.domain.service.impl;

import com.alucar.domain.model.Colaborador;
import com.alucar.domain.repository.ColaboradorRepository;
import com.alucar.domain.service.ColaboradorService;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {
    
    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorServiceImpl(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @Override
    public Colaborador criarColaborador(Colaborador colaborador) {
        return colaboradorRepository.save(colaborador);
    }

    @Override
    public Colaborador atualizarColaborador(Colaborador colaborador) {
        return colaboradorRepository.save(colaborador);
    }

    @Override
    public Colaborador buscarColaboradorPorId(Long id) {
        return colaboradorRepository.findById(id).orElse(null);
    }

    @Override
    public Colaborador excluirColaborador(Long id) {
        Colaborador colaborador = buscarColaboradorPorId(id);
        if (colaborador != null) {
            colaboradorRepository.deleteById(id);
        }
        return colaborador;
    }

    @Override
    public List<Colaborador> listarColaboradores() {
        return colaboradorRepository.findAll();
    }

    @Override
    public List<Colaborador> buscarColaboradorPorNome(String nome) {
        return colaboradorRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public void atualizarStatusColaborador(Long id, boolean status) {
        Colaborador colaborador = buscarColaboradorPorId(id);
        if (colaborador != null) {
            colaborador.setDisponivel(status);
            colaboradorRepository.save(colaborador);
        }
    }

    @Override
    public List<Colaborador> buscarColaboradorPorId(int pagina, int tamanho) {
        return colaboradorRepository.findAll(PageRequest.of(pagina, tamanho)).getContent();
    }

    @Override
    public void cadastrarColaborador(Colaborador colaborador) {
        colaboradorRepository.save(colaborador);
    }

    @Override
    public void atualizarColaborador(String id, Colaborador colaborador) {
        Long idLong = Long.valueOf(id);
        if (colaboradorRepository.existsById(idLong)) {
            colaborador.setId(idLong);
            colaboradorRepository.save(colaborador);
        }
    }

    @Override
    public void excluirColaborador(String id) {
        colaboradorRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public List<Colaborador> getColaboradoresDisponiveis(int pagina, int tamanho) {
        return colaboradorRepository.findByDisponivelTrue(PageRequest.of(pagina, tamanho)).getContent();
    }

    @Override
    public void ColaboradoresDisponiveis(Colaborador colaborador) {
        colaborador.setDisponivel(true);
        colaboradorRepository.save(colaborador);
    }

    @Override
    public Page<Colaborador> getColaboradoresDisponiveis(Pageable pageable) {
        return colaboradorRepository.findByDisponivelTrue(pageable);
    }
}