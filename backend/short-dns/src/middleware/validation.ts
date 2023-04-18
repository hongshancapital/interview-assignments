import { Response } from 'express';
import { compileValidation } from './rest-valid';
import { HttpError } from './http-error';
import { SchemaFor } from '../types/schema';
import { HttpErrorCode } from '../types/errors';

type ValFnArg<DATA> =
  ((x: object) => DATA) | SchemaFor<DATA>;
type ValFnRet<DATA> =
  ((x: DATA) => void) | SchemaFor<DATA>;

type Val<BODY extends {}, QUERY extends {}, RET> = {
  body: ValFnArg<BODY>;
  query: ValFnArg<QUERY>;
  ret: ValFnRet<RET>;
};

type ValStream<BODY extends {}, QUERY extends {}> = {
  body: ValFnArg<BODY>;
  query: ValFnArg<QUERY>;
};

export type APIFn<BOD = any, QRY = any, REQ = any, RET = any> = (b: BOD, q: QRY, req: REQ) => Promise<RET>;

type BODof<C extends APIFn> = C extends APIFn<infer BOD> ? BOD : {};
type QRYof<C extends APIFn> = C extends APIFn<any, infer QRY> ? QRY : {};
type REQof<C extends APIFn> = C extends APIFn<any, any, infer REQ> ? REQ : undefined;
type RETof<C extends APIFn> = C extends APIFn<any, any, any, infer RET> ? RET : void;

export const parseNumbers = (query: object): object => {
  const retVal: { [k: string]: any } = Object.assign({}, query);
  for (const key of Object.getOwnPropertyNames(retVal)) {
    if (retVal[key] instanceof Array) {
      retVal[key] = retVal[key].map((item: any) => isNaN(+item) ? item : +item);
    } else {
      retVal[key] = isNaN(+retVal[key]) ? retVal[key] : +retVal[key];
    }
  }
  return retVal;
};

export const api = <C extends APIFn>(
  validators: Val<BODof<C>, QRYof<C>, RETof<C>>,
  controller: C
): (req: REQof<C>, res: Response) => Promise<void> => {
  let bodyFn: (x: object) => BODof<C>;
  if (validators.body instanceof Function) {
    bodyFn = validators.body;
  } else {
    bodyFn = compileValidation<BODof<C>>(validators.body);
  }

  let queryFn: (x: object) => QRYof<C>;
  if (validators.query instanceof Function) {
    queryFn = validators.query;
  } else {
    queryFn = compileValidation<QRYof<C>>(validators.query);
  }

  let retFn: (x: RETof<C>) => void;
  if (typeof validators.ret === 'function') {
    retFn = validators.ret;
  } else {
    retFn = compileValidation<RETof<C>>(validators.ret);
  }

  return async (req: REQof<C>, res: Response) => {
    let bodyObj = req.request.body;
    let queryObj = req.request.query;
    // validate BODY
    let body: BODof<C>;
    try {
      body = bodyFn(bodyObj);
    } catch (error) {
      throw new HttpError(400, HttpErrorCode.bodyTypeInvalid);
    }

    // parseNumbers in QUERY
    queryObj = parseNumbers(queryObj);
    // validate QUERY
    let query: QRYof<C>;
    try {
      query = queryFn(queryObj);
    } catch (error) {
      throw new HttpError(400, HttpErrorCode.queryTypeInvalid);
    }

    // call
    let resData: RETof<C>;
    try {
      resData = await controller(body, query, req);
    } catch (error: any) {
      if (error instanceof HttpError) {
        throw error;
      }
      throw new HttpError(500, HttpErrorCode.systemError);
    }

    // validate OUT
    try {
      retFn(resData);
    } catch (error) {
      throw new HttpError(500, HttpErrorCode.outputTypeInvalid);
    }

    // set the result
    res.json(resData);

    return Promise.resolve();
  };
};
