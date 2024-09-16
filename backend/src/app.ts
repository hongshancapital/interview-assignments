import express, { Application } from 'express'
import router from './router';
import { ShortModel } from './model/ShortModel';
import ErrorLog from './middleware/Error';

const app: Application = express();

app.use(express.json());
app.use(router);
ShortModel.sync();
app.use(ErrorLog);

export default app;