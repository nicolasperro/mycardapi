package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Arbitro;
import com.mycard.mycardapi.model.entity.Evento;
import com.mycard.mycardapi.model.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository repository;

    public EventoService(EventoRepository repository) {
        this.repository = repository;
    }

    public Evento salvar(Evento evento) {
        validar(evento);
        return repository.save(evento);
    }

    public Optional<Evento> getEventoById(Long id) {
        return repository.findById(id);
    }

    public List<Evento> listarTodos() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Evento não encontrado");
        }
        repository.deleteById(id);
    }

    public void validar(Evento evento) {
        if (evento.getNomeEvento() == null || evento.getNomeEvento().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
        if (evento.getDataOcorrencia() == null) {
            throw new RegraNegocioException("Data de ocorrência inválida!");
        }
        if (evento.getLogradouro() == null) {
            throw new RegraNegocioException("Logradouro inválido!");
        }
        if (evento.getNumEndereco() == null) {
            throw new RegraNegocioException("Número de endereço inválido!");
        }
        if (evento.getCep() == null) {
            throw new RegraNegocioException("CEP inválido!");
        }
        if (evento.getCidade() == null) {
            throw new RegraNegocioException("Cidade inválida!");
        }
        if (evento.getUf() == null) {
            throw new RegraNegocioException("UF inválido!");
        }
        if (evento.getFuncionario() == null || evento.getFuncionario().getId() == null) {
            throw new RegraNegocioException("Funcionário inválido!");
        }
    }
}

