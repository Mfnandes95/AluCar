package com.alucar.application.mapper;

import com.alucar.domain.model.Carro;
import com.alucar.domain.model.Filial;
import com.alucar.application.dto.response.CarroResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CarroMapper {

    public Carro toEntity(CarroResponseDTO dto) {
        Carro carro = new Carro();
        carro.setId(dto.getId());
        carro.setPlaca(dto.getPlaca());
        carro.setMarca(dto.getMarca());
        carro.setModelo(dto.getModelo());
        carro.setAnoFabricacao(dto.getAnoFabricacao());
        carro.setAnoModelo(dto.getAnoModelo());
        carro.setCor(dto.getCor());
        carro.setKmAtual(dto.getKmAtual());
        carro.setStatus(dto.getStatus());
        carro.setValorDiaria(dto.getValorDiaria());
        carro.setIdCategoria(dto.getIdCategoria());

        // Não fazemos consulta ao banco aqui (o Mapper não depende de repositório);
        // criamos uma referência só com o ID, suficiente para o Hibernate gravar a FK.
        if (dto.getIdFilial() != null) {
            Filial filial = new Filial();
            filial.setIdFilial(dto.getIdFilial());
            carro.setFilial(filial);
        }

        return carro;
    }

    public CarroResponseDTO toResponseDTO(Carro carro) {
        CarroResponseDTO dto = new CarroResponseDTO();
        dto.setId(carro.getId());
        dto.setPlaca(carro.getPlaca());
        dto.setMarca(carro.getMarca());
        dto.setModelo(carro.getModelo());
        dto.setAnoFabricacao(carro.getAnoFabricacao());
        dto.setAnoModelo(carro.getAnoModelo());
        dto.setCor(carro.getCor());
        dto.setKmAtual(carro.getKmAtual());
        dto.setStatus(carro.getStatus());
        dto.setValorDiaria(carro.getValorDiaria());
        dto.setIdCategoria(carro.getIdCategoria());
        if (carro.getFilial() != null) {
            dto.setIdFilial(carro.getFilial().getIdFilial());
        }
        return dto;
    }
}