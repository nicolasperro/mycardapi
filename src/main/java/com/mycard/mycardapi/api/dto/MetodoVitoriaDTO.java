package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Atleta;
import com.mycard.mycardapi.model.entity.MetodoVitoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetodoVitoriaDTO {

    private String nomeMetodoVitoria;

    public static MetodoVitoriaDTO create(MetodoVitoria metodoVitoria) {
        ModelMapper modelMapper = new ModelMapper();
        LutaDTO dto = modelMapper.map(metodoVitoria, MetodoVitoriaDTO.class);

        dto.nomeMetodoVitoria = metodoVitoria.getNomeMetodoVitoria();
        return dto;
    }

}
