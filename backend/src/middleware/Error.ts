import RespUtil from "../utils/RespUtil";
import { NextFunction, Response, Request } from "express";

export default function ErrorLog(err: any, req: Request, res: Response, next: NextFunction) {
  RespUtil.Fail(res, err)
  next();
}