import express from 'express';
import { getKeyFromURL, getURLFromKey } from './short-link';

const app = express();

const port = 3000;

app.use(express.json());

function tryCatch(fn) {
  return (req, res, next) => {
    Promise.resolve(fn(req, res, next)).catch(next);
  };
}

app.post(
  '/short-links',
  tryCatch(async (req, res) => {
    res.send({ key: await getKeyFromURL(req.body.url) });
  })
);

app.get(
  `/short-links/:key`,
  tryCatch(async (req, res) => {
    const key = req.params.key;
    res.send({ url: await getURLFromKey(key) });
  })
);

app.use((err, req, res, next) => {
  console.log(err.stack);
  res.status(500).json({ message: err.message });
});

app.listen(port, () => {
  console.log(`app listening on port ${port}`);
});
