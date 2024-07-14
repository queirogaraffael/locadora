package com.unifacisa.locadora.resources;

import com.unifacisa.locadora.model.entities.Categoria;
import com.unifacisa.locadora.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    CategoriaService categoriaService;


    @Operation(summary = "Adiciona categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<Categoria> adicionaCategoria(@RequestBody Categoria categoria){
        Categoria cat = categoriaService.adicionaCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(cat);
    }


    @Operation(summary = "Retorna todas as categorias", description = "Não retorna com os filmes e é cacheado")
    @ApiResponse(responseCode = "200", description = "Retorna a lista de categorias")
    @GetMapping
    public ResponseEntity<List<Categoria>> retornaTodasAsCategorias(){
        List<Categoria> categorias = categoriaService.retornaTodasAsCategorias();
        return ResponseEntity.ok(categorias);
    }


    @Operation(summary = "Modifica categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.")
    })
    @PutMapping("{id}")
    public ResponseEntity<Categoria> modificaCategoria(@PathVariable Long id, @RequestBody Categoria novaCategoria){
        Categoria categoria = categoriaService.update(id, novaCategoria);
        return ResponseEntity.ok(categoria);
    }


    @Operation(summary = "Deleta categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaCategoriaPorId(@PathVariable Long id){
        categoriaService.deletaCategoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
