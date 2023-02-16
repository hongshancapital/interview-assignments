import * as dotenv from 'dotenv'
dotenv.config()
import express from 'express'
import router from './routes/index';



const app: express.Express = express();

const PORT = process.env.PORT || 3300;

app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use('/api', router);

app.listen(PORT);
