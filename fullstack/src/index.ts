import express from 'express';
import shortRouter from './routes/short';

const app = express();

app.get('/', function (req, res) {
    res.send('Hello World')
});

app.use('/', shortRouter);

app.listen('3000', () => {
    console.log('server start on http://localhost:3000')
})