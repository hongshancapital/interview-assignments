import { Router, Request, Response } from "express"
const manRouter: Router = Router()

import { getShortLinkService } from "../service/sLinkService"

// 查询
manRouter.get("/:slink", async (req: Request, res: Response) => {
  try {
    const { slink } = req.params
    if (!slink) {
      res.send("短链接异常")
      return
    }
    const serviceData = await getShortLinkService({ slink: slink })
    if (serviceData.error) {
      res.send("查询失败")
      return
    }
    const oriUrl = serviceData.data?.oriUrl
    if (!oriUrl) {
      res.send("没有找到")
      return
    }
    res.redirect(302, oriUrl)
    res.end()
    return
  } catch (e: any) {
    res.end("查询失败")
  }
})

export default manRouter
