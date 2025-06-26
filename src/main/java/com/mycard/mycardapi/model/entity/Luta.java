package com.mycard.mycardapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "luta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Luta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "atleta01_id")
    private Atleta atleta01;

    @ManyToOne
    @JoinColumn(name = "atleta02_id")
    private Atleta atleta02;

    @ManyToOne
    @JoinColumn(name = "atleta_vencedor_id")
    private Atleta atletaVencedor;

    @ManyToOne
    @JoinColumn(name = "modalidade_id")
    private Modalidade modalidade;

    @ManyToOne
    @JoinColumn(name = "organizacao_id")
    private OrganizacaoArbitragem organizacaoArbitragem;

    @ManyToOne
    @JoinColumn(name = "arbitro_id")
    private Arbitro arbitro;

    private String pesoCategoria;

    private Integer rounds;

    private Integer roundEncerramento;
}
