import './startup';
import http from 'http';
import app from '@/app';
import Mongo from '@/db/mongodb';

/**
 * Normalize a port into a number, string, or false.
 */

const normalizePort = (val: string) => {
    const port = parseInt(val, 10);

    if (isNaN(port)) {
        // named pipe
        return val;
    }

    if (port >= 0) {
        // port number
        return port;
    }

    return false;
};

/**
 * Get port from environment and store in Express.
 */

const port = normalizePort(process.env.PORT || '3000');

app.set('port', port);

/**
 * Create HTTP server.
 */

const server = http.createServer(app);


/**
 * Event listener for HTTP server "error" event.
 */

const onError = (error: Error & { syscall: string, code: string }) => {
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
};

const isHealth = () => {
    return Mongo.isOk();
};

/**
 * Event listener for HTTP server "listening" event.
 */

const onListening = () => {
    const addr = server.address();
    const bind = typeof addr === 'string'
        ? 'pipe ' + addr
        : 'port ' + addr?.port;
    const _check = setInterval(() => {
        if (!isHealth()) {
            return;
        }
        if (process.send) {
            process.send('ready');
        }
        clearInterval(_check);
        console.log('Listening on ' + bind);
    }, 1000);
};

/**
 * Listen on provided port, on all network interfaces.
 */
Mongo.connect();
server.listen(port);
server.on('error', onError);
server.on('listening', onListening);
