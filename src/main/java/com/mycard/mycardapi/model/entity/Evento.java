package com.mycard.mktapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeEvento;
    private LocalDate dataOcorrencia;
    private String logradouro;
    private String numEndereco;
    private String cep;
    private String cidade;
    private String uf;
}