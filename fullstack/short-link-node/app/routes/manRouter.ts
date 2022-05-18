import { Router, Request, Response } from "express"
import { addLinkJoiScheme, updateLinkJoiScheme, getLinkJoiScheme } from "./common/joiScheme"
import { ReqValidate, Auth } from "../descriptor/router"
import { RouterResInterface, AddLinkResInterface, UpdateLinkResInterface, GetLinkResInterface } from "./interface"
import genRes from "./common/genRes"
import genErrorCode from "../utils/errorCode"
import { ErrorInterface } from "../interface"
import { addShortLinkService, updateShortLinkService, getShortLinkService } from "../service/sLinkService"
import RouteError from "./common/RouteError"
const manRouter: Router = Router()

const genRouterError = genErrorCode("router")

class LinkRoute {
  constructor() {}
  @Auth()
  @ReqValidate(addLinkJoiScheme)
  async addLink(params: any): Promise<RouterResInterface<ErrorInterface, AddLinkResInterface>> {
    const serviceData = await addShortLinkService(params)
    return { data: serviceData.data, error: serviceData.error as ErrorInterface }
  }

  @Auth()
  @ReqValidate(updateLinkJoiScheme)
  async updateLink(params: any): Promise<RouterResInterface<ErrorInterface, UpdateLinkResInterface>> {
    const serviceData = await updateShortLinkService(params)
    return { data: serviceData.data, error: serviceData.error as ErrorInterface }
  }

  @Auth()
  @ReqValidate(getLinkJoiScheme)
  async getLink(params: any): Promise<RouterResInterface<ErrorInterface, GetLinkResInterface>> {
    const serviceData = await getShortLinkService(params)
    return { data: serviceData.data, error: serviceData.error as ErrorInterface }
  }
}
const linkRouteInstance = new LinkRoute()

// 增加
manRouter.post("/a", async (req: Request, res: Response) => {
  console.info(req.reqId, req.method, req.originalUrl, req.body, req.params)
  try {
    const body = req.body
    const retData = await linkRouteInstance.addLink(body)
    return genRes(res, retData)
  } catch (e: any) {
    const errCode = genRouterError("addShortLine") + (e.code || "99")
    const errRetData = { error: { ...e, code: errCode } } as RouterResInterface<ErrorInterface, AddLinkResInterface>
    return genRes(res, errRetData)
  }
})
// 修改
manRouter.post("/u", async (req: Request, res: Response) => {
  console.info(req.reqId, req.method, req.originalUrl, req.body, req.params)
  try {
    const body = req.body
    const retData = await linkRouteInstance.updateLink(body)
    return genRes(res, retData)
  } catch (e: any) {
    const errCode = genRouterError("updateShortLine") + (e.code || "99")
    const errRetData = { error: { ...e, code: errCode } } as RouterResInterface<ErrorInterface, AddLinkResInterface>
    return genRes(res, errRetData)
  }
})
// 查询
manRouter.get("/q", async (req: Request, res: Response) => {
  console.info(req.reqId, req.method, req.originalUrl, req.body, req.params)
  try {
    const query = req.query
    const retData: RouterResInterface<any, GetLinkResInterface> = await linkRouteInstance.getLink(query)
    return genRes(res, retData)
  } catch (e: any) {
    const errCode = genRouterError("queryShortLine") + (e?.code || "99")
    const errRetData = { error: { ...e, code: errCode } } as RouterResInterface<ErrorInterface, GetLinkResInterface>
    return genRes(res, errRetData)
  }
})

export default manRouter
