import express, { Request, Response, NextFunction } from 'express';
import bodyParser from 'body-parser';
import { environment } from './config';
import { NotFoundError, ApiError, InternalError } from './core/ApiError';
import routesV1 from './routes/v1';

const app = express();

app.use(bodyParser.json({ limit: '10mb' }));
app.use(bodyParser.urlencoded({ limit: '10mb', extended: true, parameterLimit: 50000 }));

// Routes
app.use('/v1', routesV1);

// catch 404 and forward to error handler
app.use((req, res, next) => next(new NotFoundError()));

// Middleware Error Handler
app.use((err: Error, req: Request, res: Response, next: NextFunction) => {
  if (err instanceof ApiError) {
    ApiError.handle(err, res);
  } else {
    if (environment === 'development') {
      return res.status(500).send(err.message);
    }
    ApiError.handle(new InternalError(), res);
  }
});

export default app;
