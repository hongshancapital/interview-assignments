import express from 'express';
import http from "http";
import config from './config/config';

const app = express();

app.use(express.json());
app.use(express.urlencoded({extended: false}));

const port = config.port || '3000';
app.set('port', port);

import urlRouter from "./api/controller";

app.use('/url', urlRouter);

// Create HTTP server.
const server = http.createServer(app);

export function startServer(): void {
    // Listen on provided port, on all network interfaces.
    server.listen(port);
    server.on('error', onError);
    server.on('listening', onListening);
}

export async function stopServer(): Promise<1> {
    return new Promise((resolve, reject) => {
        server.close(function (err) {
            if (err) {
                reject(err);
            } else {
                resolve(1);
                console.log(`[HTTP]server closed successfully.`);
            }
        });
    });
}

/**
 * Event listener for HTTP server "error" event.
 */
function onError(error: { syscall: string; code: any; }): void {
    if (error.syscall !== 'listen') {
        throw error;
    }

    const bind = typeof port === 'string'
        ? 'Pipe ' + port
        : 'Port ' + port;

    // handle specific listen errors with friendly messages
    switch (error.code) {
        case 'EACCES':
            console.error(bind + ' requires elevated privileges');
            process.exit(1);
            break;
        case 'EADDRINUSE':
            console.error(bind + ' is already in use');
            process.exit(1);
            break;
        default:
            throw error;
    }
}

/**
 * Event listener for HTTP server "listening" event.
 */
function onListening(): void {
    const addr = server.address();
    const bind = typeof addr === 'string'
        ? 'pipe ' + addr
        : 'port ' + addr.port;
    console.log('Listening on ' + bind);
    console.log(`-----系统启动成功-----`)
}

export default app;