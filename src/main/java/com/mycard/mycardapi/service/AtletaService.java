package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Arbitro;
import com.mycard.mycardapi.model.entity.Atleta;
import com.mycard.mycardapi.model.repository.AtletaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AtletaService {

    private final AtletaRepository repository;

    public AtletaService(AtletaRepository repository) {
        this.repository = repository;
    }


    public Atleta salvar(Atleta atleta) {
        validar(atleta);
        return repository.save(atleta);
    }

    public Optional<Atleta> getAtletaById(Long id) {
        return repository.findById(id);
    }

    public List<Atleta> listarTodos() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Atleta não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void validar(Atleta atleta) {
        if (atleta.getNomeCompleto() == null || atleta.getNomeCompleto().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
        if (atleta.getGenero() == null) {
            throw new RegraNegocioException("Gênero inválido!");
        }
        if (atleta.getNacionalidade() == null || atleta.getNacionalidade().trim().equals("")) {
            throw new RegraNegocioException("Nacionalidade inválida!");
        }

        if (atleta.getAltura() == null) {
            throw new RegraNegocioException("Altura inválida!");
        }
        if (atleta.getPesoCorporal() == null) {
            throw new RegraNegocioException("Peso inválido!");
        }
        if (atleta.getNumeroContato() == null) {
            throw new RegraNegocioException("Numero de contato inválido!");
        }
        if (atleta.getEmailContato() == null) {
            throw new RegraNegocioException("Email de contato inválido!");
        }
        if (atleta.getEquipe() == null || atleta.getEquipe().getId() == null || atleta.getEquipe().getId() == 0) {
            throw new RegraNegocioException("Equipe inválida!");
        }
    }
}

