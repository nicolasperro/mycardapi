package com.mycard.mycardapi.api.controller;

import com.mycard.mycardapi.dto.LutaDTO;
import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.*;
import com.mycard.mycardapi.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lutas")
@RequiredArgsConstructor
public class LutaController {

    private final LutaService service;
    private final EventoService eventoService;
    private final AtletaService atletaService;
    private final ArbitroService arbitroService;
    private final ModalidadeService modalidadeService;

    @GetMapping
    public ResponseEntity<List<LutaDTO>> get() {
        List<Luta> lutas = service.listarTodas();
        List<LutaDTO> dtos = lutas.stream().map(LutaDTO::create).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Luta> luta = service.buscarPorId(id);
        if (!luta.isPresent()) {
            return new ResponseEntity<>("Luta não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(LutaDTO.create(luta.get()));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody LutaDTO dto) {
        try {
            Luta luta = converter(dto);
            luta = service.salvar(luta);
            return new ResponseEntity<>(luta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody LutaDTO dto) {
        Optional<Luta> existente = service.buscarPorId(id);
        if (!existente.isPresent()) {
            return new ResponseEntity<>("Luta não encontrada", HttpStatus.NOT_FOUND);
        }

        try {
            Luta luta = converter(dto);
            luta.setId(id);
            service.salvar(luta);
            return ResponseEntity.ok(luta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<Luta> luta = service.buscarPorId(id);
        if (!luta.isPresent()) {
            return new ResponseEntity<>("Luta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Luta converter(LutaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Luta luta = modelMapper.map(dto, Luta.class);

        // Evento
        if (dto.getIdEvento() != null) {
            luta.setEvento(eventoService.getEventoById(dto.getIdEvento())
                    .orElseThrow(() -> new RegraNegocioException("Evento não encontrado")));
        }

        // Atletas
        if (dto.getIdAtleta01() != null) {
            luta.setAtleta01(atletaService.getAtletaById(dto.getIdAtleta01())
                    .orElseThrow(() -> new RegraNegocioException("Atleta 01 não encontrado")));
        }

        if (dto.getIdAtleta02() != null) {
            luta.setAtleta02(atletaService.getAtletaById(dto.getIdAtleta02())
                    .orElseThrow(() -> new RegraNegocioException("Atleta 02 não encontrado")));
        }

        if (dto.getIdAtletaVencedor() != null) {
            luta.setAtletaVencedor(atletaService.getAtletaById(dto.getIdAtletaVencedor())
                    .orElseThrow(() -> new RegraNegocioException("Atleta vencedor não encontrado")));
        }

        // Arbitro
        if (dto.getIdArbitro() != null) {
            luta.setArbitro(arbitroService.getArbitroById(dto.getIdArbitro())
                    .orElseThrow(() -> new RegraNegocioException("Árbitro não encontrado")));
        }

        // Modalidade
        if (dto.getIdModalidade() != null) {
            luta.setModalidade(modalidadeService.getModalidadeById(dto.getIdModalidade())
                    .orElseThrow(() -> new RegraNegocioException("Modalidade não encontrada")));
        }

        return luta;
    }
}
