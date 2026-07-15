package com.alucar.domain.service.impl;

import com.alucar.domain.model.Carro;
import com.alucar.domain.repository.CarroRepository;
import com.alucar.domain.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CarroServiceImpl implements CarroService {

    @Autowired
    private CarroRepository repository;

    @Override
    public Carro cadastrarCarro(Carro carro) {
        return repository.save(carro);
    }

    @Override
    public Carro getCarroPorId(String id) {
        return repository.findById(Long.valueOf(id)).orElseThrow(() -> new RuntimeException("Carro não encontrado"));
    }

    @Override
    public Carro getCarroPorPlaca(String placa) {
        return repository.findByPlaca(placa);
    }

    @Override
    public Carro getCarroDisponivelPorId(String id) {
        // Exemplo de busca personalizada: verificar se o carro está disponível
        return repository.findByIdAndDisponivelTrue(Long.valueOf(id));
    }

    @Override
    public Carro getCarroDisponivelPorPlaca(String placa) {
        return repository.findByPlacaAndDisponivelTrue(placa);
    }

    @Override
    public Page<Carro> getCarrosDisponiveis(Pageable pageable) {
        return repository.findByDisponivelTrue(pageable);
    }

    @Override
    public Page<Carro> getCarros(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void atualizarCarro(String id, Carro carro) {
        // Verifica se existe, depois salva as alterações
        repository.findById(Long.valueOf(id)).ifPresent(c -> {
            carro.setId(Long.valueOf(id));
            repository.save(carro);
        });
    }

    @Override
    public void excluirCarro(String id) {
        repository.deleteById(Long.valueOf(id));
    }
}