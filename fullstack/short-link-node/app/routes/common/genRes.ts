import { Response } from "express"
import { RouterResInterface } from "../interface"
import { ErrorInterface } from "../../interface"
function genRes<T>(res: Response, ret: RouterResInterface<ErrorInterface, T>) {
  if (ret.error) {
    const retData = { code: ret.error.code || "999999", msg: ret.error.msg, error: ret.error.e }
    console.info(res.reqId, "Error", JSON.stringify(retData))
    res.json(retData)
    return false
  } else {
    const retData = { code: "000000", msg: "ok", data: ret?.data || {} }
    console.info(res.reqId, "OK")
    res.json(retData)
    return true
  }
}

export default genRes
