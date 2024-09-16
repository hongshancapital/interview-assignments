import redisClient from "../boots/redis"
import redisConfig from "../config/redis"
import { SlinkRedisInterface } from "./interface"

const { PREFIX, EXP, SHORT_EXP } = redisConfig

function genKey(slink: string) {
  return PREFIX + slink
}

async function cleanSlink(slink: string) {
  const key = genKey(slink)
  const delRet = await redisClient.del(key)
  console.debug("cleanSlink", key, JSON.stringify(delRet))
}

async function getSlink(slink: string) {
  const key = genKey(slink)
  const retValue = await redisClient.hGetAll(key)
  console.debug("getSlink", key, retValue)
  return retValue
}

async function setSlink(slink: string, values: SlinkRedisInterface, is_short_expire?: boolean) {
  const key = genKey(slink)
  let exp = (is_short_expire ? SHORT_EXP : EXP) + Math.floor(Math.random() * 60 * 60)

  const redisMulti = redisClient.multi()
  values.isCollision && redisMulti.hSet(key, "isCollision", values.isCollision)
  values.slink && redisMulti.hSet(key, "slink", values.slink)
  values.oriUrl && redisMulti.hSet(key, "oriUrl", values.oriUrl)
  values.isDelete && redisMulti.hSet(key, "isDelete", values.isDelete)
  redisMulti.expire(key, exp)
  try {
    const redisWriteRes = await redisMulti.exec()
    console.debug("setLink, redisMulti", key, exp, values.oriUrl, JSON.stringify(redisWriteRes))
    if (redisWriteRes && redisWriteRes.length > 1 && redisWriteRes.pop()) {
      return true
    } else {
      return false
    }
    // console.log("redisWriteRes", redisWriteRes)
  } catch (e) {
    console.error(e)
  }

  // TODO 解决数据一致性问题，存入mysql的表里，然后做定时任务，同步到redis里面；或者走消息队列

  return false
  // const retValue = await redisClient.hGetAll(key)
  // return retValue
}
export { getSlink, setSlink, cleanSlink }
