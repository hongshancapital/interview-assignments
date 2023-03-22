import { Response } from 'express'
import { Code, codeType, CodeMessage } from '../constants/code'

interface ResOption {
  type?: codeType
  status?: number
  message?: any
}

// 默认成功响应
function commonRes(res: Response, data: unknown, options?: ResOption) {
  options = Object.assign({ type: Code[3000] }, options || {}) // 默认success

  const { type, status, message } = options
  let resStatus = status

  if (resStatus === undefined) {
    // 根据状态设置状态码
    resStatus = type === Code[3000] ? 200 : 409
  }

  // 响应参数
  const sendRes: { code: number; data: unknown; message?: any } = {
    code: Code[type as codeType],
    data,
  }
  // 响应描述
  message && (sendRes.message = message)

  return res.status(resStatus).send(sendRes)
}

// 错误响应
commonRes.error = function (
  res: Response,
  data: unknown,
  message?: unknown,
  status?: number
) {
  this(res, data, {
    type: 'error',
    message: message || CodeMessage['error'],
    status: status || 409,
  })
}

export default commonRes
