package com.mycard.mycardapi.controller;

import com.mycard.mycardapi.dto.LutaDTO;
import com.mycard.mycardapi.model.entity.Luta;
import com.mycard.mycardapi.service.LutaService;
import com.mycard.mycardapi.mapper.LutaMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lutas")
public class LutaController {

    private final LutaService lutaService;
    private final LutaMapper lutaMapper;

    public LutaController(LutaService lutaService, LutaMapper lutaMapper) {
        this.lutaService = lutaService;
        this.lutaMapper = lutaMapper;
    }

    @PostMapping
    public ResponseEntity<LutaDTO> criar(@RequestBody LutaDTO lutaDTO) {
        Luta luta = lutaMapper.toEntity(lutaDTO);
        Luta lutaSalva = lutaService.salvar(luta);
        return ResponseEntity.ok(lutaMapper.toDTO(lutaSalva));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LutaDTO> buscarPorId(@PathVariable Long id) {
        return lutaService.buscarPorId(id)
                .map(luta -> ResponseEntity.ok(lutaMapper.toDTO(luta)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LutaDTO>> listarTodas() {
        List<LutaDTO> lutas = lutaService.listarTodas()
                .stream()
                .map(lutaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lutas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LutaDTO> atualizar(@PathVariable Long id, @RequestBody LutaDTO lutaDTO) {
        try {
            Luta lutaAtualizada = lutaService.atualizar(id, lutaMapper.toEntity(lutaDTO));
            return ResponseEntity.ok(lutaMapper.toDTO(lutaAtualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            lutaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
