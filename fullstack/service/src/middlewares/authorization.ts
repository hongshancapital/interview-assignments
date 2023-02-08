import { Request, Response, NextFunction } from 'express';
import { JWT } from '@/services';
import { errorType, getENV } from '@/configs';

/**
 * 验证json web token
 */
export default (req: Request, res: Response, next: NextFunction): void => {
    if (req.headers.authorization) {
        const [authType, authToken] = req.headers.authorization.split(' ');

        if (authType !== 'JWT') {
            res.status(400).send('Bad request(wrong Authorization)! Refused.');
        } else {
            JWT.verify(authToken);
            next();
        }
    } else {
        res.status(400).send({
            message: 'Bad request(no Authorization)! Refused.',
            source: [getENV('SERVER_NAME')],
            code: errorType.BAD_REQUEST,
            status: 400
        } as InstanceException);
    }
};
