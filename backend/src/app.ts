import express, { Application, Request, Response } from 'express';
import { generateShortCode, validateShortCodeAndGetIdIfTrue } from '../service/codeUtil';
import { redis } from '../model/redis';
import bodyParser from 'body-parser';
import cors from 'cors';
import validator from 'validator';
import * as mysqlUtil from '../model/mysql';

export const app: Application = express();
const port = 9001;

app.use(cors());
app.use(bodyParser.json());

app.put('/longDomainName', async (req: Request, res: Response) => {
  const longDomainName = req.body.longDomainName;
  if (!longDomainName || !longDomainName.length || !validator.isURL(longDomainName)) {
    return res.sendStatus(400);
  }
  const conn = await mysqlUtil.getOneConnection();
  try {
    await conn.beginTransaction();
    const recordFromMysql = await mysqlUtil.getDomainInfoByLongName(longDomainName, conn);
    if (recordFromMysql) {
      throw new Error('Domain already registered');
    }
    const id = await mysqlUtil.insertDomainNameInMySql(longDomainName,  conn);
    const code = generateShortCode(id);
    if (code.length > 8) {
      throw new Error('Code length exceeds limit');
    }
    await redis.set(`longDomainNames${code}`, longDomainName);
    await redis.expire(`longDomainNames${code}`, 86400);
    await mysqlUtil.updateShortCodeInMySql(id, code, conn);
    await conn.commit();
    res.send(code);
  } catch (err) {
    await conn.rollback();
    res.status(409).send({ error: err.message });
  } finally {
    conn.release();
  }
});

app.get('/longDomainName/:shortCode', async (req: Request, res: Response) => {
  const code = req.params.shortCode;
  if (!code || !code.length) {
    return res.sendStatus(400);
  }
  const validResult = validateShortCodeAndGetIdIfTrue(code);
  if (!validResult.isValid) {
    return res.sendStatus(400);
  }
  const cachedDomainName = await redis.get(`longDomainNames${code}`);
  if (cachedDomainName) {
    return res.send(cachedDomainName);
  }
  const recordFromMysql = await mysqlUtil.getDomainInfoById(validResult.id);
  if (!recordFromMysql) {
    return res.sendStatus(408);
  }
  await redis.set(`longDomainNames${code}`, recordFromMysql.longName);
  await redis.expire(`longDomainNames${code}`, 86400);
  return res.send(recordFromMysql.longName);
});

export const server = app.listen(port, () => {
  // console.log(`Server started on port ${port}.`);
});