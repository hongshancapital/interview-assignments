import express from 'express';
import { Express, Response } from 'express-serve-static-core';
import helmet from 'helmet';
import dotenv from 'dotenv';
import winston from 'winston';
import expressWinston from 'express-winston';

import { Context, setContext } from './context';

export async function createServer(ctx: Context, addRouter: (app: Express) => void): Promise<Express> {
  dotenv.config();

  const server: Express = express();

  server.use(helmet());
  server.use(express.json());
  server.use(express.urlencoded({ extended: true }));
  server.use((_, res, next) => {
    setContext(res, ctx);
    next();
  });

  server.use(
    expressWinston.logger({
      transports: [new winston.transports.Console()],
      format: winston.format.combine(winston.format.json()),
    })
  );

  addRouter(server);

  server.get('/', (_, res: Response) => {
    res.status(200).json({
      message: 'Ok',
    });
  });

  server.use();

  server.use(
    expressWinston.errorLogger({
      transports: [new winston.transports.Console()],
      format: winston.format.combine(winston.format.json()),
    })
  );

  return server;
}
