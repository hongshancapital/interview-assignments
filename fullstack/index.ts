import express, { Express } from 'express';
import indexRouter from './routes/index';
import urlsRouter from './routes/urls';
import connectDB from './config/db';

connectDB();

const app: Express = express();
const PORT = process.env.PORT || 8080;

app.use('/', indexRouter);
app.use('/api', urlsRouter);

app.listen(PORT, () => {
    console.log(`http://localhost:${PORT}`);
});