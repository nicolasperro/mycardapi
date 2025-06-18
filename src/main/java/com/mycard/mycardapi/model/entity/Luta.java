package com.mycard.mycardapi.model.entity;

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

    @ManyToOne
    private Atleta atleta01;

    @ManyToOne
    private Atleta atleta02;

    @ManyToOne
    private Atleta atletaVencedor;

    @ManyToOne
    private Arbitro arbitro;

    @ManyToOne
    private Modalidade modalidade;

    private String pesoCategoria;
    private Integer rounds;
}
