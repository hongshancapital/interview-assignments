
import express, { Request, Response, Router, Express } from 'express';
import router from './route';
import DBConnect from "./dbConfigs";
import { RequestHandler } from 'express-serve-static-core';

const app: Express = express();
app.use(express.urlencoded({ extended: true }) as RequestHandler);
app.use(express.json() as RequestHandler)
DBConnect.dbConnection();
app.use(express.static('dist'));
app.get('/', (req: Request, res: Response) => {
    res.sendFile('/dist/index.html');
});
const routes: Router[] = Object.values(router);
app.use('/api', routes);
const port: number = Number(process.env.PORT) || 8050;
app.listen(port);
console.log(`App listening on ${port}`);
