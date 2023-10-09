import { createPool } from 'mysql2';

const dbOptions = {
    host: 'localhost',
    user: 'root',
    database: 'short_url',
    waitForConnections: true,
    connectionLimit: 5,
    maxIdle: 10, 
    idleTimeout: 60000, 
    queueLimit: 0,
    enableKeepAlive: true,
    keepAliveInitialDelay: 0
}

const promisePool = createPool(dbOptions).promise();
export default promisePool;
