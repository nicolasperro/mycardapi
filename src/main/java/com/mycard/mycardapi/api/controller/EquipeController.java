package com.mycard.mycardapi.api.controller;

import com.mycard.mycardapi.api.dto.EquipeDTO;
import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Equipe;
import com.mycard.mycardapi.service.EquipeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/equipes")
@RequiredArgsConstructor
public class EquipeController {

    private final EquipeService service;

    @GetMapping
    public ResponseEntity<List<EquipeDTO>> listarTodas() {
        List<EquipeDTO> dtos = service.listarTodas()
                .stream()
                .map(EquipeDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Equipe> equipe = service.getEquipeById(id);
        if (equipe.isEmpty()) {
            return new ResponseEntity<>("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(EquipeDTO.create(equipe.get()));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody EquipeDTO dto) {
        try {
            Equipe entidade = new ModelMapper().map(dto, Equipe.class);
            entidade = service.salvar(entidade);
            return new ResponseEntity<>(EquipeDTO.create(entidade), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EquipeDTO dto) {
        Optional<Equipe> existente = service.getEquipeById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Equipe entidade = new ModelMapper().map(dto, Equipe.class);
            entidade.setId(id);
            entidade = service.salvar(entidade);
            return ResponseEntity.ok(EquipeDTO.create(entidade));
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

