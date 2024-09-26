import express from 'express';
import bodyParser from 'body-parser';

import { router } from './router';
import { dataSource } from './orm';

export const app = express();

app.use(express.json());
app.use(bodyParser.urlencoded({ extended: true }))

app.use('/shorten', router);

dataSource.initialize()
    .then(data => console.log('database connect success ..'))
    .catch(err => console.log('database connect failed', err));

app.listen(5000, ()=> {
    console.log('Server running on port 5000 ...');
})