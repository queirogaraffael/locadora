package com.unifacisa.locadora.resources;

import com.unifacisa.locadora.dtos.categoria.CategoriaResponseDTO;
import com.unifacisa.locadora.dtos.filme.FilmePreviewDTO;
import com.unifacisa.locadora.dtos.filme.FilmeRequestDTO;
import com.unifacisa.locadora.dtos.filme.FilmeResponseDTO;
import com.unifacisa.locadora.dtos.filme.FilmeUpdateDTO;
import com.unifacisa.locadora.model.entities.Categoria;
import com.unifacisa.locadora.model.entities.Filme;
import com.unifacisa.locadora.services.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeResource {

    @Autowired
    private FilmeService filmeService;

    @Operation(summary = "Adiciona filme", description = "Categoria e Diretor precisam já estar persistidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Filme criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<FilmeResponseDTO> insertFilme(@RequestBody FilmeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(filmeService.insert(dto));
    }

    @Operation(summary = "Busca filmes com id, capa e url da capa DTO com suporte a paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a página de filmes com os dados solicitados"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping()
    public Page<FilmePreviewDTO> getFilmesTituloCapaDTOPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return filmeService.getFilmeTituloCapaDTOPaginados(page, size);
    }


    @Operation(summary = "Busca filme pelo id", description = "Retorna todos os dados do filme exceto categorias.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o filme"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FilmeResponseDTO> obterFilmePorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(filmeService.findById(id));
    }


    @Operation(summary = "Busca categorias de um filme.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna categoria(s) com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categorias não encontrada."),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("categorias/{idFilme}")
    public ResponseEntity<List<CategoriaResponseDTO>> obterCategoriasDeFilme(@PathVariable Long idFilme){
        return ResponseEntity.ok().body(filmeService.getCategoriasDeUmFilme(idFilme));
    }


    @Operation(summary = "Modifica filme", description = "Necessita do id do filme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filme atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FilmeResponseDTO> atualizaFilme(@PathVariable Long id, @RequestBody FilmeUpdateDTO dto) {
        return ResponseEntity.ok(filmeService.update(id, dto));
    }


    @Operation(summary = "Busca filmes com capa e url da capa DTO por categoria com suporte a paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os filmes da categoria por página"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada no sistema")
    })
    @GetMapping("/categoria/{id}")
    public Page<FilmePreviewDTO> buscaFilmesTituloCapaDTOPorCategoriaPaginados(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return filmeService.retornaFilmesTituloCapaDTOPorCategoriaPaginados(id, page, size);
    }


    @Operation(summary = "Deleta filme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Filme deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaFilmePeloId(@PathVariable Long id) {
        filmeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

