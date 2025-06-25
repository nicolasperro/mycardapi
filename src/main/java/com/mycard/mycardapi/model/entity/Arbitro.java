package com.mycard.mycardapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "arbitro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Arbitro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private OrganizacaoArbitragem organizacao;
}
