package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Arbitro;
import com.mycard.mycardapi.model.entity.Equipe;
import com.mycard.mycardapi.model.repository.EquipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipeService {

    private final EquipeRepository repository;

    public EquipeService(EquipeRepository repository) {
        this.repository = repository;
    }

    public Equipe salvar(Equipe equipe) {
        validar(equipe);
        return repository.save(equipe);
    }

    public Optional<Equipe> getEquipeById(Long id) {
        return repository.findById(id);
    }

    public List<Equipe> listarTodas() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Equipe não encontrada");
        }
        repository.deleteById(id);
    }

    public void validar(Equipe equipe) {
        if (equipe.getNomeEquipe() == null || equipe.getNomeEquipe().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
        if (equipe.getDataCriacao() == null) {
            throw new RegraNegocioException("Data de criação inválida!");
        }
        if (equipe.getLogradouro() == null) {
            throw new RegraNegocioException("Logradouro inválido!");
        }
        if (equipe.getNumEndereco() == null) {
            throw new RegraNegocioException("Número de endereço inválido!");
        }
        if (equipe.getCep() == null) {
            throw new RegraNegocioException("CEP inválido!");
        }
        if (equipe.getCidade() == null) {
            throw new RegraNegocioException("Cidade inválida!");
        }
        if (equipe.getUf() == null) {
            throw new RegraNegocioException("UF inválido!");
        }
    }
}

