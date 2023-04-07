import express, { Express } from 'express';
import { createShortUrl } from './shortUrl';
export const app: Express = express();

// parse application/json
app.use(express.json());

app.post('/shortUrl', async (req, res) => {
    res.status(200).json(await createShortUrl(req.body));
});

app.get('/:shortCode', (req, res) => {
    res.status(200).json(req.params);
});
