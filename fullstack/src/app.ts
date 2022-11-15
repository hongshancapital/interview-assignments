import express from 'express';
import bodyParser from 'body-parser';
import './mongo';
import { createShortUrl } from './controller/shorturl';
import { getLongUrl } from './controller/longurl';

const app = express();

app.use(bodyParser.json());

app.post("/shorturl/create", createShortUrl);
app.get("/longurl/get/:id", getLongUrl);

export default app;
