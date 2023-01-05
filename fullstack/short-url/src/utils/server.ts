import express from 'express';
import { Express, Response } from 'express-serve-static-core';
import helmet from 'helmet';
import dotenv from 'dotenv';
import { Context, setContext } from './context';

export async function createServer(ctx: Context): Promise<Express> {
  dotenv.config();

  const server: Express = express();

  server.use(helmet());
  server.use(express.json());
  server.use(express.urlencoded({ extended: true }));
  server.use((_, res, next) => {
    setContext(res, ctx);
    next();
  });

  server.get('/', (_, res: Response) => {
    res.status(200).json({
      message: 'Ok',
    });
  });
  return server;
}
