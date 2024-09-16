import { ErrorInterface } from "../interface"

export interface DbResInterface<ErrorInterface, T> {
  error?: ErrorInterface
  data?: T
}

// Redis 缓存数据
export interface SlinkRedisInterface {
  slink?: string
  oriUrl?: string
  isDelete?: number
  isCollision?: number
}

// 从数据库查询后的结果
export interface SlinkDbInterface {
  slink: string
  oriUrl: string
}
