package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.OrganizacaoArbitragem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizacaoArbitragemDTO {

    private Long id;
    private String nomeOrganizacao;

    public static OrganizacaoArbitragemDTO create(OrganizacaoArbitragem organizacao) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(organizacao, OrganizacaoArbitragemDTO.class);
    }
}