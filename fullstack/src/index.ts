import express from 'express';
import shortRouter from './routes/short';

const app = express();

app.use(express.json());
app.use(express.urlencoded());

app.engine('html', require('ejs').renderFile);

app.get('/', function (req, res) {
    res.render('demo.html');
});

app.use('/', shortRouter);

app.listen('3000', () => {
    console.log('server start on http://localhost:3000')
})

export default app