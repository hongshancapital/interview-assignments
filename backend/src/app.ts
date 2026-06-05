import createError from 'http-errors';
import express from 'express';
import cookieParser from 'cookie-parser';

import { domainsRouter } from '@/routes';
import { errorHandle, acceptRequestHandle, successResponseHandle } from '@/middlewares';

const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(acceptRequestHandle, successResponseHandle);

app.use('/api/v1', domainsRouter);

// catch 404 and forward to error handler
app.use((req, res, next) => {
    next(createError(404));
});

// error handler
// eslint-disable-next-line @typescript-eslint/no-unused-vars, no-unused-vars
app.use(errorHandle);

export default app;
