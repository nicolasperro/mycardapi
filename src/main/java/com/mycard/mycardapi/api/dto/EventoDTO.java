package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Atleta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {

    private String nomeEvento;
    private LocalDate dataOcorrencia;
    private Integer numEndereco;

//    private String complemento;
//    private String bairro;

    private String cidade;
    private String uf;
    private String cep;

    public static EvemtoDTO create(Evento evento) {
        ModelMapper modelMapper = new ModelMapper();
        EventoDTO dto = modelMapper.map(evento, EventoDTO.class);

        dto.nomeEvento = evento.getNomeEvento();
        dto.dataOcorrencia = evento.getDataOcorrencia();
        dto.logradouro = evento.getEndereco().getLogradouro();
        dto.numero = evento.getEndereco().getNumero();

//        dto.complemento = evento.getEndereco().getComplemento();
//        dto.bairro = evento.getEndereco().getBairro();

        dto.cidade = evento.getEndereco().getCidade();
        dto.uf = evento.getEndereco().getUf();
        dto.cep = evento.getEndereco().getCep();
        return dto;
    }

}
