package com.mycard.mycardapi.repository;

import com.mycard.mycardapi.model.entity.Luta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LutaRepository extends JpaRepository<Luta, Long> {
   }

