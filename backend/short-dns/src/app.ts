import express from 'express';
import { SERVER_PORT } from './config/env';
import router from './controller/router';

const app = express();
const PORT = SERVER_PORT;

const r = express.Router();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use('/s', router);

app.listen(PORT);
