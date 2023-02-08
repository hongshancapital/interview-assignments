import { NextFunction, Request, Response } from 'express';
import httpContext from 'express-http-context';

import { trace } from '@/configs';

/**
 * 服务器成功处理完请求返回
 */
export default (req: Request, res: Response, next: NextFunction): void => {
    res.success = (data?: unknown) => {
        trace({
            traceId: httpContext.get('traceId'),
            spanId: httpContext.get('spanId'),
            parentSpanId: httpContext.get('parentSpanId')
        }, 'return-response').info(`${req.method}: ${req.originalUrl} => \n${JSON.stringify(data, null, '   ')}`);

        res.status(200).send(data);
    };
    next();
};
