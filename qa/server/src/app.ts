import express from 'express';
import router from './routes';

const app = express();
const port = 5000;
app.use(express.json());
app.use(express.urlencoded());

app.use(router)

app.listen(port, () => {
  return console.log(`server is listening on ${port}`);
});