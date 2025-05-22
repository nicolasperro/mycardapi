package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LutaDTO {

    private Evento evento;
    private Atleta perfilAtleta1;
    private Atleta perfilAtleta2;
    private Atleta vencedor;
    private Integer quantidadeRounds;
    private Modalidade modalidade;
    private String pesoCategoria;
    private Integer roundEncerramento;

//    private LocalDate instanteEncerramento;

    private Arbitro arbitroCentral;
    private Arbitro arbitroMesa;


    public static LutaDTO create(Luta luta) {
        ModelMapper modelMapper = new ModelMapper();
        LutaDTO dto = modelMapper.map(luta, lutaDTO.class);

        dto.evento = luta.getEvento();
        dto.perfilAtleta1 = luta.getPerfilAtleta1();
        dto.perfilAtleta2 = luta.getPerfilAtleta2();
        dto.vencedor = luta.getVencedor();
        dto.quantidadeRounds = luta.getQuantidadeRounds();
        dto.modalidade = luta.getModalidade();
        dto.pesoCategoria = luta.getPesoCategoria();
        dto.roundEncerramento = luta.getRoundEncerramento();

//        dto.instanteEncerramento = luta.getInstanteEncerramento();

        dto.arbitroCentral = luta.getArbitroCentral();
        dto.arbitroMesa = luta.getArbitroMesa();
        return dto;
    }

}
