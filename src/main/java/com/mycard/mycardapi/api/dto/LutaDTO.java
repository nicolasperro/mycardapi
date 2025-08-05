package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Luta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LutaDTO {
    private Long id;

    private Long idEvento;
    private Long idAtleta1;
    private Long idAtleta2;
    private Long idVencedor;
    private Long idModalidade;
    private Long idArbitro;
    private Long idOrganizacao;

    private String evento;
    private String atleta01;
    private String atleta02;
    private String atletaVencedor;
    private String modalidade;

    private String pesoCategoria;
    private Integer rounds;
    private Integer roundEncerramento;

    public static LutaDTO create(Luta luta) {
        LutaDTO dto = new LutaDTO();
        dto.setId(luta.getId());

        if (luta.getEvento() != null) {
            dto.setIdEvento(luta.getEvento().getId());
            dto.setEvento(luta.getEvento().getNomeEvento());
        }

        if (luta.getAtleta01() != null) {
            dto.setIdAtleta1(luta.getAtleta01().getId());
            dto.setAtleta01(luta.getAtleta01().getNomeCompleto());
        }

        if (luta.getAtleta02() != null) {
            dto.setIdAtleta2(luta.getAtleta02().getId());
            dto.setAtleta02(luta.getAtleta02().getNomeCompleto());
        }

        if (luta.getAtletaVencedor() != null) {
            dto.setIdVencedor(luta.getAtletaVencedor().getId());
            dto.setAtletaVencedor(luta.getAtletaVencedor().getNomeCompleto());
        }

        if (luta.getArbitro() != null) {
            dto.setIdArbitro(luta.getArbitro().getId());
        }

        if (luta.getModalidade() != null) {
            dto.setIdModalidade(luta.getModalidade().getId());
            dto.setModalidade(luta.getModalidade().getNomeModalidade());
        }
        
        if (luta.getOrganizacaoArbitragem() != null) {
            dto.setIdOrganizacao(luta.getOrganizacaoArbitragem().getId());
        }

        dto.setPesoCategoria(luta.getPesoCategoria());
        dto.setRounds(luta.getRounds());
        dto.setRoundEncerramento(luta.getRoundEncerramento());

        return dto;
    }
}
