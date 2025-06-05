package com.mycard.mycardapi.api.controller;

import com.mycard.mycardapi.api.dto.AtletaDTO;
import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Atleta;
import com.mycard.mycardapi.model.entity.Equipe;
import com.mycard.mycardapi.service.AtletaService;
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
@RequestMapping("/api/v1/atletas")
@RequiredArgsConstructor
public class AtletaController {

    private final AtletaService service;
    private final EquipeService equipeService;

    @GetMapping
    public ResponseEntity<List<AtletaDTO>> listarTodos() {
        List<AtletaDTO> dtos = service.listarTodos()
                .stream()
                .map(AtletaDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Atleta> atleta = service.getAtletaById(id);
        if (atleta.isEmpty()) {
            return new ResponseEntity<>("Atleta não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(AtletaDTO.create(atleta.get()));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AtletaDTO dto) {
        try {
            Atleta entidade = converter(dto);
            entidade = service.salvar(entidade);
            return new ResponseEntity<>(AtletaDTO.create(entidade), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AtletaDTO dto) {
        Optional<Atleta> existente = service.getAtletaById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>("Atleta não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Atleta entidade = converter(dto);
            entidade.setId(id);
            entidade = service.salvar(entidade);
            return ResponseEntity.ok(AtletaDTO.create(entidade));
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

    private Atleta converter(AtletaDTO dto) {
        ModelMapper mapper = new ModelMapper();
        Atleta atleta = mapper.map(dto, Atleta.class);

        if (dto.getIdEquipe() != null) {
            Equipe equipe = equipeService.getEquipeById(dto.getIdEquipe())
                    .orElseThrow(() -> new RegraNegocioException("Equipe não encontrada"));
            atleta.setEquipe(equipe);
        }

        return atleta;
    }
}

