import http from 'k6/http';
import { check, sleep } from 'k6';


export let options = {
    stages: [
        { duration: '2m', target: 5000 }, 
        { duration: '5m', target: 5000 }, 
        { duration: '3m', target: 9000 }, 
        { duration: '3m', target: 9000 }, 
        { duration: '1m', target: 0 },
    ],
    
};


export default function () {

    let id = Math.floor(Math.random() * 5) + 1;

    // Pega todos os dados básicos de um filme
    let res = http.get(`http://localhost:8080/filmes/${id}`);

    check(res, {
        'status was 200': (r) => r.status === 200,
        'response time is < 500ms': (r) => r.timings.duration < 500,
    });


    // Pega todas as categorias de um filme
    res = http.get(`http://localhost:8080/filmes/categorias/${id}`);

    check(res, {
        'status was 200': (r) => r.status === 200,
        'response time is < 500ms': (r) => r.timings.duration < 500,
    });


    sleep(Math.random() * 2 + 1);

}
