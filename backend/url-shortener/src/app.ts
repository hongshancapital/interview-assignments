import express, { Express } from 'express';
export const app: Express = express();

// parse application/json
app.use(express.json());

app.post('/shortUrl', (req, res) => {
    res.status(200).json(req.body);
});

app.get('/:shortCode', (req, res) => {
    res.status(200).json(req.params);
});
