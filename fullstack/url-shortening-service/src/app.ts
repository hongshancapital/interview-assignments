import express, { Application } from 'express';
import cors from 'cors';
import helmet from 'helmet';

const app: Application = express();

app.use(helmet());
app.use(cors());
app.use(express.json());

// Router Config
app.use('/shortenUrl', require('./routes/shortenUrl'));
app.use('/getUrl', require('./routes/getUrl'));
app.use('/', require('./routes/redirect'));

export default app;
