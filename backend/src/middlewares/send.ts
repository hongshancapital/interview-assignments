import { NextFunction, Request, Response } from 'express';

export default (req: Request, res: Response, next: NextFunction): void => {
    res.success = (data?: unknown) => {
        if (req.app.get('env') === 'development') {
            console.debug(`${req.method}: ${req.originalUrl} => \n${JSON.stringify(data, null, '   ')}`);
        }
        res.status(200).send({ data });
    };
    next();
};
