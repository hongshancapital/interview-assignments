import express, { NextFunction, Request, Response } from 'express';
import cors from 'cors';
import bodyParser from 'body-parser';
import apiRouters from 'routes/index';
import { CustomError, HttpStatusCodes } from './utils/errors';

const app = express();

app.use(express.json());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors());

app.use('/api', apiRouters);

app.get('*', (_, res) => res.status(404).send('Not found 404'));

// Error handling
app.use((err: Error | CustomError, _: Request, res: Response, __: NextFunction) => {
  const status = err instanceof CustomError ? err.httpStatus : HttpStatusCodes.BAD_REQUEST;
  return res.status(status).json({
    error: err.message,
  });
});

export default app;
