package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Atleta;
import com.mycard.mycardapi.model.entity.Modalidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadeDTO {

    private String nomeModalidade;

    public static ModalidadeDTO create(Modalidade modalidade) {
        ModelMapper modelMapper = new ModelMapper();
        ModalidadeDTO dto = modelMapper.map(modalidade, ModalidadeDTO.class);

        dto.nomeModalidade = modalidade.getNomeModalidade();
        return dto;
    }

}
