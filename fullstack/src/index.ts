import express from 'express';
import { toLong, toShort } from './feature';

const app = express();
const port = 3000;

app.get('/2s/', (req, resp) => {
  const origin = req.query.link as string;
  const url = decodeURIComponent(origin);
  resp.send(toShort(url));
});

app.get('/2l/', (req, resp) => {
  const origin = req.query.short as string;
  const url = decodeURIComponent(origin);
  resp.send(toLong(url));
});

app.get('/f/:short', (req, resp) => {
  const url = toLong(req.params.short);
  if (url) {
    resp.redirect(301, url!);
    return;
  }
  resp.status(404);
  resp.send(`Not found short link: ${req.params.short}`);
});

app.listen(port, () => console.log(`listen on http://localhost:${port}`));
