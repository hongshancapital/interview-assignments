import { Request, Response, NextFunction } from 'express';

export default (req: Request, _res: Response, next: NextFunction): void => {
    if (req.app.get('env') === 'development') {
        let _datas = `\nheader: ${JSON.stringify(req.headers, null, '   ')}`;

        if (Object.getOwnPropertyNames(req.body).length > 0) {
            _datas += `\nbody: ${JSON.stringify(req.body, null, '   ')}`;
        }
        if (Object.getOwnPropertyNames(req.query).length > 0) {
            _datas += `\nquery: ${JSON.stringify(req.query, null, '   ')}`;
        }
        if (Object.getOwnPropertyNames(req.params).length > 0) {
            _datas += `\nparams: ${JSON.stringify(req.params, null, '   ')}`;
        }

        console.debug(`${req.method}: ${req.originalUrl} ${_datas}`);
    }

    next();
};
