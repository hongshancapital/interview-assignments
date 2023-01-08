import express from 'express';
import { Express, NextFunction, Request, Response } from 'express-serve-static-core';
import helmet from 'helmet';
import dotenv from 'dotenv';
import expressWinston from 'express-winston';

import { Context, setContext } from './context';
import { errorHandler } from '@/common/error-handler';
import { loggerOptions } from '@/common/logging';

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

  server.use(expressWinston.logger(loggerOptions));

  addRouter(server);

  server.get('/', (_, res: Response) => {
    res.status(200).json({
      message: 'Ok',
    });
  });

  server.use(expressWinston.errorLogger(loggerOptions));

  server.use((err: Error, req: Request, res: Response, _: NextFunction) => {
    errorHandler.handleError(err, res);
  });

  return server;
}
