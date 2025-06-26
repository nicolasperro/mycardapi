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
    public ResponseEntity<List<EquipeDTO>> get() {
        List<Equipe> equipes = service.listarTodas();
        List<EquipeDTO> dtos = equipes.stream()
                .map(EquipeDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<Equipe> equipe = service.getEquipeById(id);
        if (equipe.isEmpty()) {
            return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(EquipeDTO.create(equipe.get()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody EquipeDTO dto) {
        try {
            Equipe equipe = converter(dto);
            equipe = service.salvar(equipe);
            return new ResponseEntity(EquipeDTO.create(equipe), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody EquipeDTO dto) {
        if (service.getEquipeById(id).isEmpty()) {
            return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Equipe equipe = converter(dto);
            equipe.setId(id);
            equipe = service.salvar(equipe);
            return ResponseEntity.ok(EquipeDTO.create(equipe));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        try {
            // A verificação de existência já é feita no seu service.
            service.deletar(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Equipe converter(EquipeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        // Conversão direta, pois não há entidades relacionadas para buscar.
        return modelMapper.map(dto, Equipe.class);
    }
}