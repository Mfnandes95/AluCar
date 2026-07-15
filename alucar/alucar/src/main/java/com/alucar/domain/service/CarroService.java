package com.alucar.domain.service;

import com.alucar.domain.model.Carro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CarroService {

    Carro cadastrarCarro(Carro carro);
    Carro getCarroPorId(String id);
    Carro getCarroPorPlaca(String placa);
    Carro getCarroDisponivelPorId(String id);
    Carro getCarroDisponivelPorPlaca(String placa);

    Page<Carro> getCarrosDisponiveis(Pageable pageable);
    Page<Carro> getCarros(Pageable pageable);
    void atualizarCarro(String id, Carro carro);
    void excluirCarro(String id);
}