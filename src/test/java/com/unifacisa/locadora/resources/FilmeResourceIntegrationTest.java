package com.unifacisa.locadora.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifacisa.locadora.model.entities.Categoria;
import com.unifacisa.locadora.model.entities.Filme;
import com.unifacisa.locadora.repositories.CategoriaRepository;
import com.unifacisa.locadora.repositories.FilmeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Testes do controlador de Filme")
class FilmeResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Teste para o endpoint POST /filmes")
    void testInsertFilme() throws Exception {
        // Cria uma categoria existente para o filme
        Categoria categoria = new Categoria();
        categoria.setNome("Ação");
        categoriaRepository.save(categoria);

        Filme filme = new Filme();
        filme.setTitulo("Novo Filme");
        filme.setDescricao("Descrição do Novo Filme");
        filme.setDataLancamento("2011");
        filme.setDuracao("120 minutos");
        filme.setCategorias(Set.of(categoria));

        mockMvc.perform(post("/filmes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filme)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Novo Filme"));
    }


    @Test
    @DisplayName("Teste para o endpoint GET /filmes com paginação")
    void testGetFilmesPaginados() throws Exception {
        mockMvc.perform(get("/filmes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }


    @Test
    @DisplayName("Teste para o endpoint GET /filmes/{id}")
    void testObterFilmePorId() throws Exception {
        Filme filme = new Filme();
        filme.setTitulo("Filme Teste");
        filme.setDescricao("Descrição Teste");
        filme.setDataLancamento("2011");
        filme.setDuracao("90 minutos");
        filmeRepository.save(filme);

        mockMvc.perform(get("/filmes/" + filme.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Filme Teste"));
    }


    @Test
    @DisplayName("Teste para o endpoint PUT /filmes/{id}")
    void testAtualizaFilme() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setNome("Ação");
        categoriaRepository.save(categoria);


        Filme filme = new Filme();
        filme.setTitulo("Filme Atualizar");
        filme.setDescricao("Descrição Atualizar");
        filme.setDataLancamento("2011");
        filme.setRating(8.9);
        filme.setDuracao("100 minutos");
        filme.setCapaUrl("url capa");
        filme.setTrailerUrl("trailer url");
        filme.setVideoUrl("filme url");
        filme.setCategorias(Set.of(categoria));
        filmeRepository.save(filme);

        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setNome("categoria atualizada");
        categoriaRepository.save(categoriaAtualizada);

        Filme filmeAtualizado = new Filme();
        filmeAtualizado.setTitulo("Filme Atualizado");
        filmeAtualizado.setDescricao("Descrição Atualizada");
        filmeAtualizado.setDataLancamento("2011");
        filmeAtualizado.setRating(9.0);
        filmeAtualizado.setDuracao("105 minutos");
        filmeAtualizado.setCapaUrl("url capa atualizado");
        filmeAtualizado.setTrailerUrl("trailer url atualizada");
        filmeAtualizado.setVideoUrl("filme url atualizada");
        filmeAtualizado.setCategorias(Set.of(categoriaAtualizada));

        mockMvc.perform(put("/filmes/" + filme.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmeAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Filme Atualizado"));
    }


    @Test
    @DisplayName("Teste para o endpoint DELETE /filmes/{id}")
    void testDeletaFilmePeloId() throws Exception {
        Filme filme = new Filme();
        filme.setTitulo("Filme Deletar");
        filme.setDescricao("Descrição Deletar");
        filme.setDataLancamento("2011");
        filme.setDuracao("110 minutos");
        filmeRepository.save(filme);

        mockMvc.perform(delete("/filmes/" + filme.getId()))
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Teste para o endpoint GET /filmes/categoria/{id} com paginação")
    void testBuscaFilmesDTOPorCategoriaPaginados() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setNome("Drama");
        categoriaRepository.save(categoria);

        Filme filme = new Filme();
        filme.setTitulo("Filme Categoria");
        filme.setDescricao("Descrição Categoria");
        filme.setDataLancamento("2011");
        filme.setDuracao("130 minutos");
        filme.setCategorias(Set.of(categoria));
        filmeRepository.save(filme);

        mockMvc.perform(get("/filmes/categoria/" + categoria.getId())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}
