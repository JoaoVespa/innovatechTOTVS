package br.com.innovatech.api;

import br.com.innovatech.model.Transcricao;
import br.com.innovatech.service.TranscricaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transcricoes")
public class TranscricaoController {

    private final TranscricaoService service;

    public TranscricaoController(TranscricaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            List<Transcricao> lista = service.listarTodos();
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Falha ao listar transcricoes"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") int id) {
        try {
            Transcricao t = service.buscarPorId(id);

            if (t == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("erro", "Transcricao nao encontrada"));
            }

            return ResponseEntity.ok(t);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Falha ao buscar transcricao"));
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody Transcricao t) {
        try {
            Transcricao criada = service.inserir(t);
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Falha ao cadastrar transcricao"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable("id") int id,
            @RequestBody Transcricao t) {
        try {
            Transcricao atualizada = service.atualizar(id, t);

            if (atualizada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("erro", "Transcricao nao encontrada"));
            }

            return ResponseEntity.ok(atualizada);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Falha ao atualizar transcricao"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") int id) {
        try {
            boolean excluida = service.excluir(id);

            if (!excluida) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("erro", "Transcricao nao encontrada"));
            }

            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Falha ao excluir transcricao"));
        }
    }
}