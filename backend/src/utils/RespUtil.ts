import { Response } from "express";
import ResCode from "../common/RespCode";

export default class RespUtil {
  public static Success(res: Response, data: any, status = ResCode.Code.SUCCESS) {
    const [code, msg] = ResCode.getCode(status);
    res.send({ code: code, data: data, msg });
  }

  public static Fail(res: Response, data: any = null, status = ResCode.Code.FAILED) {
    RespUtil.Success(res, data, status)
  }

  public static Redirect(res: Response, data: string) {
    res.redirect(302, data);
  }
}