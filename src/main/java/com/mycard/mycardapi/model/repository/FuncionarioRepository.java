package com.mycard.mycardapi.model.repository;

import com.mycard.mycardapi.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByCpf(String cpf);
    Optional<Funcionario> findByEmailContato(String emailContato);
}

