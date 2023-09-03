import { BaseBLogic } from "../blogic/base-blogic";
import { BLogicContext, ResultData } from "../common/base";
import { DBProxy } from "../common/db-proxy";
import { PermissionException } from "../common/exception/permission-exception";
import { SystemException } from "../common/exception/system-exception";
import { logger } from "../common/logger";

export class BaseRouter {
  public static post(router: any, path: string, BLogicClass: any) {
    logger.info("bind post router " + path);
    router.post(path, async (req: any, res: any, next: any) => {
      const reqNo: string = "Req" + Math.ceil(Math.random() * 9999);
      const dbproxy: DBProxy = new DBProxy();
      dbproxy.connect();
      const blogic: BaseBLogic = new BLogicClass();
      const originalUrl: string = req.originalUrl;
      let data: any = path.includes("upload") ? { req: req } : req.body;

      let session: any = { host: req.get("host") };
      let context: BLogicContext<any> = {
        session: session,
        input: data,
        params: req.params,
        proxy: dbproxy,
        originalUrl,
        reqNo,
      };
      let resData: ResultData = { ok: true, data: {} };
      try {
        const r = await blogic.execute(context);
        resData.data = r;
      } catch (err) {
        logger.error(JSON.stringify(err));
        resData.ok = false;
        if (err instanceof SystemException) {
          resData.err_code = err.code;
          resData.err_msg = err.msg;
        } else if (err instanceof PermissionException) {
          resData.err_code = err.code;
          resData.err_msg = err.msg;
        }
      }
      res.send(resData);
      dbproxy.disconnect();
    });
  }
}
