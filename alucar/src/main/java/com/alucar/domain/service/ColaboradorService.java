package com.alucar.domain.service;

import java.util.List;

public interface ColaboradorService {
    
    ColaboradorService criarColaborador(ColaboradorService colaborador);
    ColaboradorService atualizarColaborador(ColaboradorService colaborador);
    ColaboradorService buscarColaboradorPorId(Long id);
    ColaboradorService excluirColaborador(Long id);

    List<ColaboradorService> listarColaboradores();
    List<ColaboradorService> buscarColaboradorPorNome(String nome);
}