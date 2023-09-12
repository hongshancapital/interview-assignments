import express, { Express } from 'express';
import config from 'config';
import router from './api';

const app: Express = express();

app.use(express.json());
app.use('/', router);

const server = app.listen(
    config.get<number>('server.port')
);
export default server;