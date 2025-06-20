# 🎬 Locadora API

A **Locadora API** é uma aplicação desenvolvida em **Java 17** com **Spring Boot**, projetada para gerenciar filmes e categorias de forma eficiente e escalável. A API utiliza **PostgreSQL** como banco de dados, cache com **Redis** e possui suporte a **paginação**, documentação interativa com **Swagger**, e testes de performance com **K6**. O projeto também está containerizado com **Docker** e **Docker Compose**, o que facilita a execução e implantação.

---

## 📚 Visão Geral

O projeto adota uma **arquitetura em camadas**, separando responsabilidades para garantir manutenibilidade e escalabilidade:

- Entidades representam as tabelas do banco de dados.
- DTOs são utilizados para transferir dados entre as camadas sem expor diretamente as entidades.
- Repositórios fazem a interface com o banco utilizando Spring Data JPA.
- Serviços contêm a lógica de negócio da aplicação.
- Controllers expõem os endpoints REST para o consumo da API.

---

## 🧩 Funcionalidades Principais

- CRUD completo para filmes e categorias.
- Paginação na listagem de filmes e categorias.
- Associação de múltiplas categorias a um filme.
- Validação de dados e tratamento robusto de erros.
- Documentação automática e interativa com Swagger.
- Cache implementado com Redis para maior desempenho.
- Containerização com Docker e orquestração via Docker Compose.
- Testes de performance utilizando a ferramenta K6.

---

## ⚙️ Tecnologias Utilizadas

- Java 17
- Spring Boot (Spring Web, Spring Data JPA, Spring Validation, Spring Cache)
- PostgreSQL
- Redis
- Lombok
- Swagger/OpenAPI
- Docker e Docker Compose
- K6 (para testes de carga e desempenho)

---

## 📦 Estrutura do Projeto

A estrutura do projeto é organizada em pastas que representam claramente cada camada: controllers, serviços, entidades, DTOs, repositórios e configurações. Também estão incluídos os arquivos para Docker e configuração da aplicação.

---

## 🔧 Configurações

O banco de dados utilizado é o **PostgreSQL**, com as configurações de acesso definidas no arquivo de propriedades. O Redis é configurado como provedor de cache.

---

## 🐳 Docker

O projeto possui um Dockerfile que gera a imagem da aplicação, e um arquivo `docker-compose.yml` que orquestra a aplicação, o banco de dados PostgreSQL e o Redis.

---

## 📘 Documentação Swagger

A documentação da API está disponível via Swagger, permitindo visualizar e testar os endpoints de forma simples e interativa através do navegador.

---

## 🛠 Exemplos de Endpoints com Paginação

A API suporta paginação nas requisições de filmes e categorias, com parâmetros para definir o número da página e a quantidade de itens por página. Por exemplo:

- Listar filmes com título e capa paginados
- Listar filmes por categoria com suporte à paginação
- Listar categorias com suporte à paginação

---

## 🚀 Como Executar Localmente

Para executar o projeto localmente:

1. Clone o repositório.
2. Gere o build com Maven.
3. Suba os serviços com Docker Compose (`docker-compose up`).
4. A aplicação estará disponível localmente para testes e uso.
