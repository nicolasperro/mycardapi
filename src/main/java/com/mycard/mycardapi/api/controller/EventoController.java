package com.mycard.mycardapi.api.controller;

import com.mycard.mycardapi.api.dto.EventoDTO;
import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Evento;
import com.mycard.mycardapi.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService service;

    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarTodos() {
        List<EventoDTO> dtos = service.listarTodos()
                .stream()
                .map(EventoDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Evento> evento = service.getEventoById(id);
        if (evento.isEmpty()) {
            return new ResponseEntity<>("Evento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(EventoDTO.create(evento.get()));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody EventoDTO dto) {
        try {
            Evento entidade = new ModelMapper().map(dto, Evento.class);
            entidade = service.salvar(entidade);
            return new ResponseEntity<>(EventoDTO.create(entidade), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EventoDTO dto) {
        Optional<Evento> existente = service.getEventoById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>("Evento não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Evento entidade = new ModelMapper().map(dto, Evento.class);
            entidade.setId(id);
            entidade = service.salvar(entidade);
            return ResponseEntity.ok(EventoDTO.create(entidade));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

