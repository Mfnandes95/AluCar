package com.alucar.application.mapper;

import com.alucar.domain.model.Carro;
import com.alucar.application.dto.response.CarroResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CarroMapper {
    
    public Carro toEntity(CarroResponseDTO carroResponseDTO) {
        Carro carro = new Carro();
        carro.setId(carroResponseDTO.getId());
        carro.setModelo(carroResponseDTO.getModelo());
        carro.setMarca(carroResponseDTO.getMarca());
        carro.setAno(carroResponseDTO.getAno());
        carro.setTipoCombustivel(carroResponseDTO.getTipoCombustivel());
        carro.setQuilometragem(carroResponseDTO.getQuilometragem());
        carro.setCor(carroResponseDTO.getCor());
        carro.setPlaca(carroResponseDTO.getPlaca());
        carro.setValorDiaria(carroResponseDTO.getValorDiaria());
        return carro;
    }

    public CarroResponseDTO toResponseDTO(Carro carro) {
        CarroResponseDTO carroResponseDTO = new CarroResponseDTO();
        carroResponseDTO.setId(carro.getId());
        carroResponseDTO.setModelo(carro.getModelo());
        carroResponseDTO.setMarca(carro.getMarca());
        carroResponseDTO.setAno(carro.getAno());
        carroResponseDTO.setTipoCombustivel(carro.getTipoCombustivel());
        carroResponseDTO.setQuilometragem(carro.getQuilometragem());
        carroResponseDTO.setCor(carro.getCor());
        carroResponseDTO.setPlaca(carro.getPlaca());
        carroResponseDTO.setValorDiaria(carro.getValorDiaria());
        return carroResponseDTO;
    }
}