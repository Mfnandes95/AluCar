package com.alucar.application.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alucar.application.dto.response.CarroResponseDTO;
import com.alucar.application.mapper.CarroMapper;
import com.alucar.domain.model.Carro;
import com.alucar.domain.service.CarroService;

@RestController
public class CarroController {
    
    private final CarroService carroService;
    private final CarroMapper carroMapper;

    public CarroController(CarroService carroService, CarroMapper carroMapper) {
        this.carroService = carroService;
        this.carroMapper = carroMapper;
    }

    @PostMapping("/carros")
    public ResponseEntity<CarroResponseDTO> cadastrarCarro(@RequestBody CarroResponseDTO carroResponseDTO) {
        Carro carro = carroMapper.toEntity(carroResponseDTO);
        Carro carroCadastrado = carroService.cadastrarCarro(carro);
        CarroResponseDTO carroResponseDTOCadastrado = carroMapper.toResponseDTO(carroCadastrado);
        return ResponseEntity.status(HttpStatus.CREATED).body(carroResponseDTOCadastrado);
    }

    @PostMapping("/carros/{id}")
    public ResponseEntity<Void> atualizarCarro(@PathVariable String id, @RequestBody CarroResponseDTO carroResponseDTO) {
        Carro carro = carroMapper.toEntity(carroResponseDTO);
        carroService.atualizarCarro(id, carro);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carros/{id}")
    public ResponseEntity<Void> excluirCarro(@PathVariable String id) {
        carroService.excluirCarro(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/carros-disponiveis")
    public ResponseEntity<List<CarroResponseDTO>> listarCarrosDisponiveis(
            @RequestParam int pagina, 
            @RequestParam int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Carro> carrosDisponiveis = carroService.getCarrosDisponiveis(pageable);
        List<CarroResponseDTO> carrosDisponiveisDTO = carrosDisponiveis.stream()    
                .map(carroMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carrosDisponiveisDTO);
    }
}
