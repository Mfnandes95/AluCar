package com.alucar.domain.service;

import com.alucar.domain.model.Colaborador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ColaboradorService {
    
    Colaborador criarColaborador(Colaborador colaboradorResponseDTO);
    Colaborador atualizarColaborador(Colaborador colaborador);
    Colaborador buscarColaboradorPorId(Long id);
    Colaborador excluirColaborador(Long id);


    List<Colaborador> listarColaboradores();
    List<Colaborador> buscarColaboradorPorNome(String nome);
    void atualizarStatusColaborador(Long id, boolean status);
    List<Colaborador> buscarColaboradorPorId(int pagina, int tamanho);
    void cadastrarColaborador(Colaborador colaborador);
    void atualizarColaborador(String id, Colaborador colaborador);
    void excluirColaborador(String id);
    List<Colaborador> getColaboradoresDisponiveis(int pagina, int tamanho);
    void ColaboradoresDisponiveis(Colaborador colaborador);
    Page<Colaborador> getColaboradoresDisponiveis(Pageable pageable); 
    
}