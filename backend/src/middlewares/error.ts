import { Request, Response, NextFunction } from 'express';


export default (err: Error & { status: number }, req: Request, res: Response, _next: NextFunction) => { /* eslint-disable-line*/
    const { status, message } = err;

    console.error(err);
    res.status(status || 500).send({
        message,
        data: null
    });
};
