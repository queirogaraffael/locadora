import http from 'k6/http';
import { sleep, check } from 'k6';

export let options = {
    vus: 1,
    duration: '1m',

};

// A aplicação precisa ter acabado de começar a rodar.
let count = 1;


export default function () {


    // Cria categoria 
    let response = http.post('http://localhost:8080/categorias', JSON.stringify({
        nome: 'Categoria'
    }), { headers: { 'Content-Type': 'application/json' } });

    check(response, {
        'status is 201': (r) => r.status === 201,

    })

    sleep(1);


    // Pega todas as categorias 
    response = http.get('http://localhost:8080/categorias');

    check(response, {
        'status is 200': (r) => r.status === 200,

    });
    sleep(1);


    // Modifica categoria
    response = http.put(`http://localhost:8080/categorias/${count}`, JSON.stringify({
        nome: 'Categoria Modificada'
    }), { headers: { 'Content-Type': 'application/json' } });

    check(response, {
        'status is 200': (r) => r.status === 200,

    });

    sleep(1);


    // Deleta categoria
    response = http.del(`http://localhost:8080/categorias/${count}`);

    check(response, {
        'status is 204': (r) => r.status === 204,

    });

    sleep(1)

    count++

}






