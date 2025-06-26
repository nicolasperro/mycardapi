package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Evento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {

    private Long id;
    private String nomeEvento;
    private LocalDate dataOcorrencia;
    private String logradouro;
    private String numEndereco;
    private String cep;
    private String cidade;
    private String uf;

    public static EventoDTO create(Evento evento) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(evento, EventoDTO.class);
    }
}