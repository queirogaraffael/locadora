import http from 'k6/http';
import { check, sleep } from 'k6';


export let options = {
    stages: [
        { duration: '30s', target: 5000 },
        { duration: '1m', target: 5000 },
        { duration: '30s', target: 9000 },
        { duration: '2m', target: 9000 },
        { duration: '30s', target: 0 },
    ],
};


export default function () {


    // Pega todos os dados básicos de um filme
    let res = http.get('http://localhost:8080/filmes/1');

    check(res, {
        'status was 200': (r) => r.status === 200,
    });


    // Pega todas as categorias de um filme
    res = http.get('http://localhost:8080/filmes/categorias/1');

    check(res, {
        'status was 200': (r) => r.status === 200,
    });


    sleep(Math.random() * 2 + 1);

}
