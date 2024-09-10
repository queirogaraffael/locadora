import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 1,
    duration: '20s'
};


export default function () {

    let response;

    // Cria categoria 
    response = http.post('http://localhost:8080/categorias', JSON.stringify({
        nome: 'Categoria Teste'
    }), { headers: { 'Content-Type': 'application/json' } });

    check(response, {
        'status is 201': (r) => r.status === 201,

    });

    let categoria = response.json().toString;
    let idCategoria = response.json().id;

    sleep(1);


    // Cria filme
    response = http.post('http://localhost:8080/filmes', JSON.stringify({
        titulo: 'titulo',
        descricao: 'descricao',
        dataLancamento: new Date().toISOString().split('T')[0],
        rating: 8.8,
        duracao: '2h',
        capaUrl: 'link capa URL',
        trailerUrl: 'trailer URL',
        videoUrl: 'Video URL',
        categorias: categoria
    }), { headers: { 'Content-Type': 'application/json' } });

    check(response, {
        'status is 201': (r) => r.status === 201,

    });

    let filmeId = response.json().id;

    sleep(1);


    // Busca filmes DTOs paginados
    response = http.get('http://localhost:8080/filmes?page=0&size=10');

    check(response, {
        'status is 200': (r) => r.status === 200,

    });

    sleep(1);


    // Busca filme pelo ID
    response = http.get(`http://localhost:8080/filmes/${filmeId}`);

    check(response, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1);


    // Modifica filme // vai precisar
    response = http.put(`http://localhost:8080/filmes/${filmeId}`, JSON.stringify({

        titulo: 'titulo modificado',
        descricao: 'descricao modificada',
        dataLancamento: new Date().toISOString().split('T')[0],
        rating: 8.0,
        duracao: '3h',
        capaUrl: 'link capa URL modificado',
        trailerUrl: 'trailer URL modificado',
        videoUrl: 'Video URL modificado',
        categorias: categoria

    }), { headers: { 'Content-Type': 'application/json' } });


    check(response, {
        'status is 200': (r) => r.status === 200,

    });

    sleep(1);


    // Busca filme por categoria paginados
    response = http.get(`http://localhost:8080/filmes/categoria/${idCategoria}`);

    check(response, {
        'status is 200': (r) => r.status === 200,

    });

    sleep(1);


    // Deleta filme
    response = http.del(`http://localhost:8080/filmes/${filmeId}`);

    check(response, {
        'status is 204': (r) => r.status === 204,
    });

    sleep(1);

}