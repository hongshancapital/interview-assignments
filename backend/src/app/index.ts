import express, { Express, Request, Response } from 'express';
import router from './router'

const app: Express = express();

app.use(express.json())
app.use('/', router);

const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {

})


export default app;
