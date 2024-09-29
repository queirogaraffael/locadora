# Locadora API
O projeto locadora é uma API para gerenciamento de filmes em uma locadora. Desenvolvida em Java 17 com Spring Boot, a API utiliza Redis para caching e oferece várias funcionalidades para manipulação de dados sobre filmes.
Arquitetura do Projeto

## O projeto segue uma arquitetura em camadas, composta por:
1. Entidades: Representam os dados armazenados no banco de dados. Exemplo: Filme.
2. Repositórios: Interfaces que interagem com o banco de dados utilizando Spring Data JPA. Exemplo: FilmeRepository.
3. Serviços: Contêm a lógica de negócios. Exemplo: FilmeService.
4. Recursos: Endpoints da API para manipulação de recursos. Exemplo: FilmeResource.

## Ferramentas Utilizadas
* Java 17: Linguagem de programação utilizada.
* Spring Boot: Framework para simplificar a configuração e o desenvolvimento de aplicações Java.
* Spring Data JPA: Abstração para interações com o banco de dados.
* Spring Web: Módulo para desenvolvimento de aplicações web.
* H2 Database: Banco de dados em memória para testes e desenvolvimento.
* Springdoc OpenAPI: Documentação interativa da API.
* Redis: Sistema de cache utilizado para otimizar o desempenho.
* Maven: Gerenciamento de dependências e construção do projeto.

## Configuração do Projeto
### Banco de Dados H2
A configuração do banco de dados H2 é feita no arquivo application.properties:

application.properties:

```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.jpa.properties.hibernate.ddl-auto=create-drop
```

### Configurações de Cache com Redis
Para habilitar o caching com Redis:

application.properties:

```
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379
```

### Docker (Opcional)
Para facilitar o uso do Redis, você pode utilizar o Docker no windows:

```
docker run --name my-redis -p 6379:6379 -d redis
docker exec -it my-redis sh
redis-cli
127.0.0.1:6379> keys *
```

## Como Executar
Para executar a aplicação:
1. Clone o repositório para o seu ambiente local.
2. Navegue até o diretório do projeto.
3. Execute a classe LocadoraApplication.
4. Acesse a aplicação através do seu navegador web.

## Endpoints da API
A API possui os seguintes endpoints:
* Adicionar Filme
    POST /filmes
    Adiciona um novo filme.
* Listar Todos os Filmes
    GET /filmes
    Retorna a lista de todos os filmes.
* Obter Filme por ID
    GET /filmes/{id}
    Retorna um filme específico pelo ID.
* Atualizar Filme
    PUT /filmes/{id}
    Atualiza um filme pelo ID.
* Deletar Filme
    DELETE /filmes/{id}
    Deleta um filme pelo ID.

## Documentação da API
A documentação da API é gerada automaticamente pelo Springdoc OpenAPI e pode ser acessada através do endpoint /swagger-ui.html.