import cookieParser from 'cookie-parser';
import morgan from 'morgan';
import helmet from 'helmet';
import express, { Request, Response, NextFunction } from 'express';

import 'express-async-errors';

import BaseRouter from './routes/api';
import logger from 'jet-logger';
import EnvVars from './declarations/major/EnvVars';
import HttpStatusCodes from './declarations/major/HttpStatusCodes';
import { NodeEnvs } from './declarations/enums';
import { RouteError } from './declarations/classes';
import { AppDataSource } from "./data-source";
// **** Init express **** //

const app = express();
AppDataSource.initialize().then(() => {
  // **** Set basic express settings **** //
  app.use(express.json());
  app.use(express.urlencoded({ extended: true }));
  app.use(cookieParser(EnvVars.cookieProps.secret));

  // Show routes called in console during development
  if (EnvVars.nodeEnv === NodeEnvs.Dev) {
    app.use(morgan('dev'));
  }

  // Security
  if (EnvVars.nodeEnv === NodeEnvs.Production) {
    app.use(helmet());
  }


  // **** Add API routes **** //

  // Add APIs
  app.use('/api', BaseRouter);

  // Setup error handler
  app.use((
    err: Error,
    _: Request,
    res: Response,
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    next: NextFunction,
  ) => {
    logger.err(err, true);
    let status = HttpStatusCodes.BAD_REQUEST;
    if (err instanceof RouteError) {
      status = err.status;
    }
    return res.status(status).json({ error: err.message });
  });
});

// **** Serve front-end content **** //

// **** Export default **** //

export default app;
