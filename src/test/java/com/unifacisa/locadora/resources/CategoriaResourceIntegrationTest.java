package com.unifacisa.locadora.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifacisa.locadora.model.entities.Categoria;
import com.unifacisa.locadora.repositories.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoriaResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAdicionaCategoria_Success() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setNome("Categoria Teste");

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Categoria Teste"));
    }

    @Test
    void testRetornaTodasAsCategorias_Success() throws Exception {
        // Setup inicial no banco
        categoriaRepository.save(new Categoria(null,"Categoria 1"));
        categoriaRepository.save(new Categoria(null,"Categoria 2"));

        mockMvc.perform(get("/categorias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testModificaCategoria_Success() throws Exception {
        // Setup inicial no banco
        Categoria categoria = categoriaRepository.save(new Categoria(null,"Categoria Antiga"));

        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome("Categoria Nova");

        mockMvc.perform(put("/categorias/{id}", categoria.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaCategoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Categoria Nova"));
    }

    @Test
    void testModificaCategoria_NotFound() throws Exception {
        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome("Categoria Inexistente");

        mockMvc.perform(put("/categorias/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaCategoria)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletaCategoriaPorId_Success() throws Exception {
        Categoria categoria = categoriaRepository.save(new Categoria(null,"Categoria para Deletar"));

        mockMvc.perform(delete("/categorias/{id}", categoria.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletaCategoriaPorId_NotFound() throws Exception {
        mockMvc.perform(delete("/categorias/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
