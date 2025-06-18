package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Modalidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadeDTO {

    private Long id;
    private String nomeModalidade;

    public static ModalidadeDTO create(Modalidade modalidade) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(modalidade, ModalidadeDTO.class);
    }
}