import logger from '../utils/logger';
import { Request, Response, NextFunction } from 'express';

const emptyFunc = () => {};
// todo something if nessary
const preProcessMap = new Map([
  ['get', (req: Request, next: NextFunction) => {
    logger.debug(`*** ${req.originalUrl}, [get], params: ${JSON.stringify(req.query)}`);
    next();
  }],
  ['post', (req: Request, next: NextFunction) => {
    logger.debug(`*** ${req.originalUrl}, [post], params: ${JSON.stringify(req.body)}`);
    next();
  }],
  ['option', (req: Request, next: NextFunction) => {
    // ...
    next();
  }],
  ['put', (req: Request, next: NextFunction) => {
    // ...
    next();
  }],
  ['delete', (req: Request, next: NextFunction) => {
    // ...
    next();
  }]
]);

export const preProcess = (req: Request, res: Response, next: NextFunction) => {
  if (preProcessMap.has(req.method)) {
    const process = preProcessMap.get(req.method) || emptyFunc;
    process(req, next);
  } else {
    next();
  }
};
