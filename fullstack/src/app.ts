import express, {Application} from 'express';

import * as controller from './controllers/shortener_controller'

const app: Application = express();
app.use(express.json()); 
app.use(express.urlencoded());

app.get('/go/:shortUrl', controller.getLongUrl);
app.post('/shorten', controller.generateShortUrl);
app.listen(5000, () => console.log('Server Started'));

export default app;
