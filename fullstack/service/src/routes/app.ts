import express from 'express';
import path from 'path';
import fs from 'fs';
import compression from 'compression';
import httpContext from 'express-http-context';
import { errorType, getENV } from '@/configs';

const app = express();

/**常用中间件 */
app.use(express.json({ limit: '10mb' }));
app.use(express.urlencoded({ extended: false, limit: '10mb' }));
app.use(compression());
app.use(httpContext.middleware);

app.use(express.static(path.resolve(__dirname, '../../public/doc')));

app.get('/', (_req, res) => {
    if (getENV('NODE_ENV') === 'development') {
        const apidoc = path.resolve(__dirname, '../../public/doc/index.html');

        if (fs.existsSync(apidoc)) {
            res.sendFile(apidoc);
        } else {
            res.send('文档未找到或未生成.');
        }
    } else {
        res.send('welcome');
    }
});

import {
    // JWTCheckHandle,
    acceptRequestHandle,
    successResponseHandle,
    errorHandle
} from '@/middlewares';

/**自定义中间件 */
// app.use(JWTCheckHandle);
app.use(acceptRequestHandle);
app.use(successResponseHandle);

import v1 from './v1';

app.use('/api/v1', v1);


app.use('*', (req) => {
    throw new Exception(`url: [{${req.method.toLowerCase()}} => ${req.originalUrl}] not found!`, errorType.URL_NOT_FOUND);
});
app.use(errorHandle);

export default app;
