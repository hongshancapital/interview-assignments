import 'reflect-metadata';

import express, { Request, Response, NextFunction } from 'express';
import { errors } from 'celebrate';
import 'express-async-errors';

import BizError from '@error/BizError';
import routes from './routes';

import '@base/mongodb';
import '@repositories/container';

const app = express();

app.use(express.json());
app.use('/api', routes);
app.use(errors());``

app.use((err: Error, request: Request, response: Response, _: NextFunction) => {
  if (err instanceof BizError) {
    return response.status(400).json({
      status: 'error',
      message: err.message,
    });
  }

  return response.status(500).json({
    status: 'error',
    message: 'Internal server error',
  });
});

export default app;
