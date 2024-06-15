# Locadora Application
Este é um projeto de uma aplicação de locadora de filmes desenvolvido em Spring Boot. A aplicação permite realizar operações CRUD (Criar, Ler, Atualizar e Deletar) em uma entidade Filme.
Estrutura do Projeto

## O projeto está organizado em:
* LocadoraApplication: Classe principal que inicializa a aplicação.
* Entities: Define a entidade Filme.
* Repositories: Interface FilmeRepository para interações com o banco de dados.
* Resources: Endpoints REST para operações com filmes.
* Services: Lógica de negócios para manipulação de filmes.
* Exceptions: Tratamento de exceções específicas.

## Como Executar
* Clone o repositório: Faça o download do código-fonte do projeto a partir do repositório remoto para sua máquina local.
* Navegue até o diretório do projeto: Utilize um terminal ou prompt de comando para acessar o diretório onde o projeto foi clonado.
* Execute a aplicação: No diretório do projeto, execute o comando para iniciar a aplicação. A aplicação Spring Boot será iniciada e ficará disponível para acesso. A aplicação está configurada para rodar no perfil de teste, utilizando o banco de dados H2 em memória.
* Teste a aplicação: Você pode testar os endpoints utilizando o Postman ou qualquer ferramenta similar. Use os endpoints disponíveis em http://localhost:8080/filmes para interagir com a aplicação.

## Endpoints
A aplicação segue princípios RESTful para o design de seus endpoints:
* POST /filmes: Adiciona um novo filme.
* GET /filmes: Lista todos os filmes.
* GET /filmes/{id}: Obtém um filme pelo ID.
* PUT /filmes/{id}: Atualiza um filme existente.
* DELETE /filmes/{id}: Remove um filme pelo ID.

## Dependências
* Spring Boot
* Spring Data JPA
* H2 Database