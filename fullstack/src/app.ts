import express, { Express, Request, Response } from 'express';
import bodyParser from 'body-parser';


const app: Express = express();

app.use(bodyParser.json());

app.post('/shortlink', function (req: Request, res: Response) {
    let url: String = req.body.url;
});

app.get('/shortlink', function (req: Request, res: Response) {
    let hash: String = req.params.hash;
})

export { app }
