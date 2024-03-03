/**
 * 返回结果dto
 */
interface HostResponse {
  code: number
  message: string
  data: HostData
}

interface HostData {
  longHost: string
  shortHost: string
}

export { HostResponse, HostData }