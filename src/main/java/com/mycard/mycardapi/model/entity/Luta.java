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
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "atleta01_id", nullable = false)
    private Atleta atleta01;

    @ManyToOne
    @JoinColumn(name = "atleta02_id", nullable = false)
    private Atleta atleta02;

    @ManyToOne
    @JoinColumn(name = "atleta_vencedor_id")
    private Atleta atletaVencedor;

    @ManyToOne
    @JoinColumn(name = "modalidade_id", nullable = false)
    private Modalidade modalidade;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private OrganizacaoArbitragem organizacaoArbitragem;

    @ManyToOne
    @JoinColumn(name = "arbitro_id", nullable = false)
    private Arbitro arbitro;

    private String pesoCategoria;

    private Integer rounds;

    private Integer roundEncerramento;
}
