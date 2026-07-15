package com.alucar.domain.service.impl;

import com.alucar.domain.model.Carro;
import com.alucar.domain.repository.CarroRepository;
import com.alucar.domain.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarroServiceImpl implements CarroService {

    private static final String STATUS_DISPONIVEL = "disponivel";

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
        return repository.findByIdAndStatus(Long.valueOf(id), STATUS_DISPONIVEL);
    }

    @Override
    public Carro getCarroDisponivelPorPlaca(String placa) {
        return repository.findByPlacaAndStatus(placa, STATUS_DISPONIVEL);
    }

    @Override
    public Page<Carro> getCarrosDisponiveis(Pageable pageable) {
        return repository.findByStatus(STATUS_DISPONIVEL, pageable);
    }

    @Override
    public Page<Carro> getCarros(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void atualizarCarro(String id, Carro carro) {
        repository.findById(Long.valueOf(id)).ifPresent(c -> {
            carro.setId(Long.valueOf(id));
            repository.save(carro);
        });
    }

    @Override
    public void excluirCarro(String id) {
        repository.deleteById(Long.valueOf(id));
    }

    @Override
    public List<Carro> getCarrosDisponiveisNoPeriodo(LocalDate inicio, LocalDate fim) {
        return repository.findDisponiveisNoPeriodo(inicio, fim);
    }
}