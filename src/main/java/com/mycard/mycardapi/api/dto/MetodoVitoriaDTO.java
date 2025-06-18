package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.MetodoVitoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetodoVitoriaDTO {

    private Long id;
    private String titulo; 

    public static MetodoVitoriaDTO create(MetodoVitoria metodoVitoria) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(metodoVitoria, MetodoVitoriaDTO.class);
    }
}