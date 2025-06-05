package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Arbitro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArbitroDTO {

    private String nomeArbitro;
    private LocalDate dataNascimento;

    public static ArbitroDTO create(Arbitro arbitro) {
        ModelMapper modelMapper = new ModelMapper();
        ArbitroDTO dto = modelMapper.map(arbitro, ArbitroDTO.class);

        dto.nomeArbitro = arbitro.getNomeArbitro();
        dto.dataNascimento = arbitro.getDataNascimento();
        return dto;
    }

}
