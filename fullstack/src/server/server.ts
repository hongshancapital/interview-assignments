import * as express from 'express';
import { Express } from 'express';
import apiRouter from './routes';
const app: Express = express();

app.use(express.static('public'));
app.use(apiRouter);

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`Server listening on port: ${port}`));
