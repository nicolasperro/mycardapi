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
    public ResponseEntity<List<EventoDTO>> get() {
        List<Evento> eventos = service.listarTodos();
        List<EventoDTO> dtos = eventos.stream()
                .map(EventoDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<Evento> evento = service.getEventoById(id);
        if (evento.isEmpty()) {
            return new ResponseEntity("Evento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(EventoDTO.create(evento.get()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody EventoDTO dto) {
        try {
            Evento evento = converter(dto);
            evento = service.salvar(evento);
            return new ResponseEntity(EventoDTO.create(evento), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody EventoDTO dto) {
        if (service.getEventoById(id).isEmpty()) {
            return new ResponseEntity("Evento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Evento evento = converter(dto);
            evento.setId(id);
            evento = service.salvar(evento);
            return ResponseEntity.ok(EventoDTO.create(evento));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        try {
            // A verificação de existência do evento já é feita no seu service
            service.deletar(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Evento converter(EventoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        // Conversão direta, pois não há outras entidades para buscar
        return modelMapper.map(dto, Evento.class);
    }
}