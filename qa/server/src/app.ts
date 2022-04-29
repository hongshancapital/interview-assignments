import express from 'express';
import router from './routes';
import cors from 'cors';

const app = express();
const port = 5000;
const corsOptions = {
  origin: '*',
  methods: "GET,HEAD,PUT,PATCH,POST,DELETE",
  preflightContinue: false,
  allowedHeaders: '*',
};
app.options('*', cors(corsOptions));
app.use(cors());
app.use(express.json());
app.use(express.urlencoded());

app.use(router)

app.listen(port, () => {
  return console.log(`server is listening on ${port}`);
});