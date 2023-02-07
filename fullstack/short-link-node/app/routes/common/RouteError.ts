import { ErrorInterface } from "../../interface"
export default class RouteError implements ErrorInterface {
  code: string
  msg: string | undefined
  e: Error | any | undefined
  constructor(err: string | Error | any, msg?: string | Error, e?: Error) {
    if (typeof err === "string") {
      this.code = err
      if (typeof msg === "string") {
        this.msg = msg
      } else if (msg instanceof Error) {
        this.msg = msg["message"] || ""
        this.e = msg
      }
    } else if (err instanceof Error) {
      this.code = "999999"
      this.msg = err["message"] || "未知错误"
      this.e = err["stack"] || err
    } else if (typeof err === "object") {
      this.code = err["code"] || "999999"
      this.msg = err["msg"] || err["message"] || "未知错误"
      this.e = err["stack"] || ""
    } else {
      this.code = "999999"
      this.msg = "未知错误"
    }
  }
}
