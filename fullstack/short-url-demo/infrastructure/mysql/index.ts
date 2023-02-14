import {createPool, Pool } from 'mysql';

const pool:Pool = createPool({
    connectionLimit : 5,
    host     : 'localhost',
    port     : 3306,
    user     : 'test',
    password : '123456',
    database : 'short_url'
});
console.log("mysql connecting...");

export default {
    pool
};