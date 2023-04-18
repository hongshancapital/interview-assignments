import { Router, Request, Response, RequestHandler, NextFunction } from 'express';
import { HttpError } from './http-error';
import { HttpErrorCode } from '../types/errors';

type METHODS = 'GET' | 'POST';

export type ReqEnhReq = {
  request: Request;
};

export class APIRouter {
  public expressRouter: Router;
  constructor(express?: Router) {
    this.expressRouter = express ? express : Router();
  }
  public route(
    method: METHODS,
    rt: string,
    ctrl: (req: ReqEnhReq, res: Response) => Promise<void>
  ) {
    let statusOK = method === 'POST' ? 201 : 200;
    let expressCtrl: RequestHandler = (req: Request, res: Response, done: NextFunction) => {
      res.status(statusOK);
      ctrl({ request: req }, res)
        .then(() => { done(); })
        .catch((err: HttpError) => {
          if (err instanceof HttpError) {
            res.status(err.status).json({
              code: err.code,
            });
            return;
          } else {
            res.status(500).json({ code: HttpErrorCode.systemError });
            return;
          }
        });
    };
    switch (method) {
      case 'GET':
        this.expressRouter.get(rt, expressCtrl);
        break;
      case 'POST':
        this.expressRouter.post(rt, expressCtrl);
        break;
      default: return;
    }
  }
}
