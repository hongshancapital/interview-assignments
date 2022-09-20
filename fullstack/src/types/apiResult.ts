enum ResponseStatus {
  success = 200,
  exists = 201,
  fail = 500,
  sysNotInit,
  sysInited,
  notLoggin,
  paramError
}
/**
 * 接口返回json统一结构
 */
class ApiResult{
  status: ResponseStatus
  data: string | object | null
  msg?: string
  constructor (status: ResponseStatus, data?: string | object | null, message?: string) {
    this.status = status
    this.data = data
    this.msg = message
  }
}
export {
  ApiResult,
  ResponseStatus
}