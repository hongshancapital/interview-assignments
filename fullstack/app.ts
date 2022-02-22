import open from 'open';
import cors from 'cors';
import express from 'express';
import bodyParser from 'body-parser';
import logger from './server/utils/logger';
import db from './server/models';
import { preProcess } from './server/middleware/params';

const port = 3001;
const host = '127.0.0.1';
const app = express();
const corsOptions = {
  origin: "http://127.0.0.1:3000"
};

const shortLinkRouter = require('./server/router/short-link');

db.sequelize.sync();

app.use(cors(corsOptions));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(preProcess);

app.use('/api', shortLinkRouter);

app.listen(port, () => {

  logger.log(`🚀🚀🚀 Server is running on port ${port}`);

  open(`http://${host}:${port}/api`);
});

export default app;
