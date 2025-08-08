package com.mycard.mycardapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organizacao_arbitragem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizacaoArbitragem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeOrganizacao;

    @OneToMany(mappedBy = "organizacao", cascade = CascadeType.ALL)
    private List<Arbitro> arbitros;
}
