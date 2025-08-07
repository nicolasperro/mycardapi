package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Arbitro;
import com.mycard.mycardapi.model.repository.ArbitroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArbitroService {

    private final ArbitroRepository repository;

    public ArbitroService(ArbitroRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Arbitro salvar(Arbitro arbitro) {
        validar(arbitro);
        return repository.save(arbitro);
    }

    public Optional<Arbitro> getArbitroById(Long id) {
        return repository.findById(id);
    }

    public List<Arbitro> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Árbitro não encontrado!");
        }
        repository.deleteById(id);
    }

    public void validar(Arbitro arbitro) {
        if (arbitro.getNomeArbitro() == null || arbitro.getNomeArbitro().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
        if (arbitro.getDataNascimento() == null) {
            throw new RegraNegocioException("Data de nascimento inválida!");
        }
        if (arbitro.getOrganizacao() == null || arbitro.getOrganizacao().getId() == null) {
            throw new RegraNegocioException("Organização inválida!");
        }
    }
}
