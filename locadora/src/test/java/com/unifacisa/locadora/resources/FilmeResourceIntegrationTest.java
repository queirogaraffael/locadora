package com.unifacisa.locadora.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifacisa.locadora.dtos.filme.FilmeUpdateDTO;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Testes do controlador de Filme")
@ActiveProfiles("test")
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
        Categoria categoria = new Categoria();
        categoria.setNome("Ação");
        categoriaRepository.save(categoria);

        Filme filme = new Filme();
        filme.setTitulo("Novo Filme");
        filme.setDescricao("Descrição do Novo Filme");
        filme.setDataLancamento(LocalDate.parse("2025-06-14"));
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
        filme.setDataLancamento(LocalDate.parse("2025-06-14"));
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
        filme.setDataLancamento(null);
        filme.setCategorias(new HashSet<>(Set.of(categoria)));
        filmeRepository.save(filme);

        FilmeUpdateDTO filmeAtualizado = new FilmeUpdateDTO("Filme Atualizado", "Descrição Atualizada", LocalDate.parse("2025-06-14")
                ,"url capa atualizado");

        mockMvc.perform(put("/filmes/" + filme.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmeAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Filme Atualizado"))
                .andExpect(jsonPath("$.descricao").value("Descrição Atualizada"))
                .andExpect(jsonPath("$.dataLancamento").value("2025-06-14"))
                .andExpect(jsonPath("$.capaUrl").value("url capa atualizado"));

    }


    @Test
    @DisplayName("Teste para o endpoint DELETE /filmes/{id}")
    void testDeletaFilmePeloId() throws Exception {
        Filme filme = new Filme();
        filme.setTitulo("Filme Deletar");
        filme.setDescricao("Descrição Deletar");
        filme.setDataLancamento(LocalDate.parse("2025-06-14"));
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
        filme.setDataLancamento(LocalDate.parse("2025-06-14"));
        filme.setCategorias(Set.of(categoria));
        filmeRepository.save(filme);

        mockMvc.perform(get("/filmes/categoria/" + categoria.getId())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}
