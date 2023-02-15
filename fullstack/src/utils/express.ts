import Express from 'express';
import { ParamsDictionary, Query } from 'express-serve-static-core';

export interface ResBody<T> {
  success: boolean;
  data?: T;
  errMsg?: string;
  errCode?: string;
}

export type RequestHandler<ResData, ReqBody, ReqQuery = Query> = Express.RequestHandler<
  ParamsDictionary,
  ResBody<ResData>,
  ReqBody,
  ReqQuery
>;

export type Request<ResData, ReqBody, ReqQuery = Query> = Express.Request<
  ParamsDictionary,
  ResBody<ResData>,
  ReqBody,
  ReqQuery
>;

export type Response<ResData> = Express.Response<ResBody<ResData>>;

export function successBody<T>(data: T): ResBody<T> {
  return {
    success: true,
    data,
  };
}

export function errorBody<T>(errCode: string, errMsg: string): ResBody<T> {
  return {
    success: false,
    errCode,
    errMsg,
  };
}
