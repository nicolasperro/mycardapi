package com.mycard.mycardapi.controller;

import com.mycard.mycardapi.model.entity.Modalidade;
import com.mycard.mycardapi.service.ModalidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modalidades")
public class ModalidadeController {

    private final ModalidadeService modalidadeService;

    public ModalidadeController(ModalidadeService modalidadeService) {
        this.modalidadeService = modalidadeService;
    }

    @PostMapping
    public ResponseEntity<Modalidade> criar(@RequestBody Modalidade modalidade) {
        Modalidade criada = modalidadeService.salvar(modalidade);
        return ResponseEntity.ok(criada);
    }

    @GetMapping
    public ResponseEntity<List<Modalidade>> listarTodas() {
        return ResponseEntity.ok(modalidadeService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modalidade> buscarPorId(@PathVariable Long id) {
        return modalidadeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nomeModalidade}")
    public ResponseEntity<Modalidade> buscarPorNome(@PathVariable String nomeModalidade) {
        return modalidadeService.buscarPorNome(nomeModalidade)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modalidade> atualizar(@PathVariable Long id, @RequestBody Modalidade modalidade) {
        try {
            Modalidade atualizada = modalidadeService.atualizar(id, modalidade);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            modalidadeService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
