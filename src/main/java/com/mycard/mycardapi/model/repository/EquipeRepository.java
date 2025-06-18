package com.mycard.mycardapi.model.repository;

import com.mycard.mycardapi.model.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
     }
