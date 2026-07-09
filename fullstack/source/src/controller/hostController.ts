import { Express, Request, Response, Router } from 'express'
import hostService from "../service/hostService"
import log4js from "log4js";
import { HostResponse, HostData } from '../type/hostResponse'

const router = Router();
const logger = log4js.getLogger("app")
/**
 * 短域名存储接口
 */
router.get("/save", async (req: Request, res: Response) => {
  let response: HostResponse = {
    code: 0,
    message: "",
    data: null
  }
  // 参数校验
  
  let longHost: string = req.query.longHost as string
  if (longHost == "" || longHost == null || longHost == undefined) {
    response = {
      code: -1,
      message: "参数异常",
      data: null
    }
    response.code = -1
    response.message = "参数异常"
    res.send(response);
    logger.warn("参数异常 params:", req.params)
    return
  }
  // 执行逻辑
  try {
    response.data = await hostService.save(longHost);
  } catch (e) {
    response.code = -2
    response.message = "服务端异常"
    res.send(response)
    logger.error("hostService.save error:", e)
    return
  }
  res.send(response);
});

/**
 * 短域名查询接口
 */
router.get("/search", async (req: Request, res: Response) => {
  let response: HostResponse = {
    code: 0,
    message: "",
    data: null
  }
  // 参数校验
  let shortHost: string = req.query.shortHost as string
  if (shortHost == "" || shortHost == null || shortHost == undefined) {
    response.code = -1
    response.message = "参数异常"
    res.send(response);
    logger.warn("参数异常 params:{}", req.params)
    return
  }
  // 执行逻辑
  try {
    response.data = await hostService.search(shortHost);
  } catch (e) {
    response.code = -2
    response.message = "服务端异常"
    res.send(response)
    logger.error("hostService.search error:", e)
    return
  }
  res.send(response);
});

export default router;
