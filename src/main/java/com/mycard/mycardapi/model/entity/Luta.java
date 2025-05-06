package com.mycard.mktapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Luta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Evento evento;
    private Atleta atleta01;
    private Atleta atleta02;
    private Atleta atletaVencedor;
    private Arbitro arbitro;
    private Modalidade modalidade;
    private String pesoCategoria;
    private Integer rounds;
}
