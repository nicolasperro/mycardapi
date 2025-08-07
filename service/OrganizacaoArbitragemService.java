package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Arbitro;
import com.mycard.mycardapi.model.entity.OrganizacaoArbitragem;
import com.mycard.mycardapi.model.repository.OrganizacaoArbitragemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizacaoArbitragemService {

    private final OrganizacaoArbitragemRepository repository;

    public OrganizacaoArbitragemService(OrganizacaoArbitragemRepository repository) {
        this.repository = repository;
    }

    public OrganizacaoArbitragem salvar(OrganizacaoArbitragem org) {
        validar(org);
        return repository.save(org);
    }

    public Optional<OrganizacaoArbitragem> getOrganizacaoById(Long id) {
        return repository.findById(id);
    }

    public List<OrganizacaoArbitragem> listarTodas() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Organização de arbitragem não encontrada");
        }
        repository.deleteById(id);
    }

    public void validar(OrganizacaoArbitragem organizacaoArbitragem) {
        if (organizacaoArbitragem.getNomeOrganizacao() == null || organizacaoArbitragem.getNomeOrganizacao().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
    }
}

