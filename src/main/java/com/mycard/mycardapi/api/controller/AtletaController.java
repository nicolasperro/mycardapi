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
    public ResponseEntity<List<AtletaDTO>> get() {
        List<Atleta> atletas = service.listarTodos();
        List<AtletaDTO> dtos = atletas.stream()
                .map(AtletaDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<Atleta> atleta = service.getAtletaById(id);
        if (atleta.isEmpty()) {
            return new ResponseEntity("Atleta não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(AtletaDTO.create(atleta.get()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody AtletaDTO dto) {
        try {
            Atleta atleta = converter(dto);
            atleta = service.salvar(atleta);
            return new ResponseEntity(AtletaDTO.create(atleta), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody AtletaDTO dto) {
        if (service.getAtletaById(id).isEmpty()) {
            return new ResponseEntity("Atleta não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Atleta atleta = converter(dto);
            atleta.setId(id);
            atleta = service.salvar(atleta);
            return ResponseEntity.ok(AtletaDTO.create(atleta));
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

    private Atleta converter(AtletaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Atleta atleta = modelMapper.map(dto, Atleta.class);

        // Lógica para buscar e setar a equipe a partir do ID
        if (dto.getIdEquipe() != null) {
            Equipe equipe = equipeService.getEquipeById(dto.getIdEquipe())
                    .orElseThrow(() -> new RegraNegocioException("Equipe com id " + dto.getIdEquipe() + " não encontrada."));
            atleta.setEquipe(equipe);
        }

        return atleta;
    }
}