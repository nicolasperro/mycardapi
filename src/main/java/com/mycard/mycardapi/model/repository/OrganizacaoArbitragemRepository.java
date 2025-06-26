package com.mycard.mycardapi.model.repository;

import com.mycard.mycardapi.model.entity.OrganizacaoArbitragem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacaoArbitragemRepository extends JpaRepository<OrganizacaoArbitragem, Long> {
}

