package com.mycard.mycardapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataOcorrencia;

    private String logradouro;
    private String numero;
    private String cep;
    private String cidade;
    private String uf;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Luta> lutas;
}
