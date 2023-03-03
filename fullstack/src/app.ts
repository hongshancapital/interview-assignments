import { init as initDao } from './service/dao';
import * as bodyparser from 'body-parser';
const express = require('express')
const app = express()
const linkreq = require('./controller/linkreq');
const querylink = require('./controller/querylink');
const port = 3000

app.use(bodyparser.json());

app.post('/linkreq', async(req, res) => {
  const result = await linkreq(req?.body || {});
  res.send(result);
})

app.post('/querylink', async (req, res) => {
  const result = await querylink(req?.body || {});
  res.send(result);
})

app.listen(port, async () => {
  await initDao();
  console.log(`link service listening on port ${port}`)
})

export { app };