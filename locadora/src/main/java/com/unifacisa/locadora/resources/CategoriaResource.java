package com.unifacisa.locadora.resources;

import com.unifacisa.locadora.dtos.categoria.CategoriaRequestDTO;
import com.unifacisa.locadora.dtos.categoria.CategoriaResponseDTO;
import com.unifacisa.locadora.dtos.categoria.CategoriaUpdateDTO;
import com.unifacisa.locadora.services.CategoriaService;
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
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;


    @Operation(summary = "Adiciona categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> adicionaCategoria(@RequestBody CategoriaRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.adicionaCategoria(dto));
    }


    @Operation(summary = "Retorna todas as categorias paginada")
    @ApiResponse(responseCode = "200", description = "Retorna a lista de categorias")
    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDTO>> retornaTodasAsCategorias(){
        return ResponseEntity.ok(categoriaService.retornaTodasAsCategoriasPaginadas());
    }


    @Operation(summary = "Modifica categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PutMapping("{id}")
    public ResponseEntity<CategoriaResponseDTO> modificaCategoria(@PathVariable Long id, @RequestBody CategoriaUpdateDTO dto){
        return ResponseEntity.ok(categoriaService.update(id, dto));
    }


}
