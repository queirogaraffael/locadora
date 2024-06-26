package com.unifacisa.locadora.resources;

import com.unifacisa.locadora.entities.Filme;
import com.unifacisa.locadora.services.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filmes")
public class FilmeResource {

    @Autowired
    private FilmeService filmeService;

    @Operation(description = "Adiciona filme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Filme criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<Filme> salvar(@RequestBody Filme filme) {
        Filme novoFilme = filmeService.insert(filme);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFilme);
    }


    @Operation(description = "Busca todos os filmes")
    @ApiResponse(responseCode = "200", description = "Retorna a lista de filmes")
    @GetMapping
    public ResponseEntity<List<Filme>> listarTodos() {
        List<Filme> filmes = filmeService.findAll();
        return ResponseEntity.ok(filmes);
    }


    @Operation(description = "Busca filme pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o filme"),
            @ApiResponse(responseCode = "404", description = "Não existe filme com este ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Filme> obterPorId(@PathVariable Long id) {
        Optional<Filme> optionalFilme = filmeService.findById(id);
        if (optionalFilme.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            Filme filme = optionalFilme.get();
            return ResponseEntity.ok().body(filme);
        }
    }

    @Operation(description = "Modifica filme pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filme atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Filme com ID não encontrado.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Filme> update(@PathVariable Long id, @RequestBody Filme filme) {
        Optional<Filme> optionalFilme = filmeService.findById(id);

        if (optionalFilme.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Filme updatedFilme = filmeService.update(id, filme);
            return ResponseEntity.ok(updatedFilme);
        }
    }


    @Operation(description = "Deleta filme pelo id")
    @ApiResponse(responseCode = "204", description = "Filme deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        filmeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

