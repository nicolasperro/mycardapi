package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Evento;
import com.mycard.mycardapi.model.repository.EventoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import recomendado

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository repository;

    public EventoService(EventoRepository repository) {
        this.repository = repository;
    }

    @Transactional // Garante que a operação seja atômica
    public Evento salvar(Evento evento) {
        // Aqui você poderia adicionar validações de negócio antes de salvar
        return repository.save(evento);
    }

    @Transactional(readOnly = true) // Otimização para operações de apenas leitura
    public Optional<Evento> getEventoById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true) // Otimização para operações de apenas leitura
    public List<Evento> listarTodos() {
        return repository.findAll();
    }

    @Transactional // Garante que a operação seja atômica
    public void deletar(Long id) {
        // Esta é a versão otimizada
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Evento com ID " + id + " não encontrado."));
        repository.delete(evento);
    }
}