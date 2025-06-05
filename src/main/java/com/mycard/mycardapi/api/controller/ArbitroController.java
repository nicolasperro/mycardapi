package com.mycard.mycardapi.api.controller;

import com.mycard.mycardapi.api.dto.ArbitroDTO;
import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Arbitro;
import com.mycard.mycardapi.model.entity.OrganizacaoArbitragem;
import com.mycard.mycardapi.service.ArbitroService;
import com.mycard.mycardapi.service.OrganizacaoArbitragemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/arbitros")
@RequiredArgsConstructor
public class ArbitroController {

    private final ArbitroService arbitroService;
    private final OrganizacaoArbitragemService organizacaoService;

    @GetMapping
    public ResponseEntity<List<ArbitroDTO>> getAll() {
        List<Arbitro> arbitros = arbitroService.listarTodos();
        List<ArbitroDTO> dtos = arbitros.stream()
                .map(ArbitroDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Arbitro> arbitro = arbitroService.buscarPorId(id);
        if (arbitro.isEmpty()) {
            return new ResponseEntity<>("Árbitro não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ArbitroDTO.create(arbitro.get()));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ArbitroDTO dto) {
        try {
            Arbitro arbitro = converter(dto);
            arbitro = arbitroService.salvar(arbitro);
            return new ResponseEntity<>(ArbitroDTO.create(arbitro), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ArbitroDTO dto) {
        Optional<Arbitro> existente = arbitroService.buscarPorId(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>("Árbitro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Arbitro arbitro = converter(dto);
            arbitro.setId(id);
            arbitro = arbitroService.salvar(arbitro);
            return ResponseEntity.ok(ArbitroDTO.create(arbitro));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            arbitroService.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Arbitro converter(ArbitroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Arbitro arbitro = modelMapper.map(dto, Arbitro.class);

        if (dto.getIdOrganizacao() != null) {
            OrganizacaoArbitragem organizacao = organizacaoService.getOrganizacaoById(dto.getIdOrganizacao())
                    .orElseThrow(() -> new RegraNegocioException("Organização de arbitragem não encontrada"));
            arbitro.setOrganizacao(organizacao);
        }

        return arbitro;
    }
}
