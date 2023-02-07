import { DbResInterface } from "./interface"
import { ErrorInterface } from "../interface"
import mysqlClint from "../boots/mysql"
import { getSlink, setSlink } from "./redisClient"
import { Link } from "../entity/link"
import { SlinkRedisInterface, SlinkDbInterface } from "./interface"
import { getHashId, isShortId } from "./hash"

export async function query(slink: string): Promise<string> {
  const redisRet: SlinkRedisInterface = await getSlink(slink)
  console.debug("query from redis", slink, JSON.stringify(redisRet))
  if (redisRet.oriUrl) {
    // setSlink(slink, redisRet)
    console.debug("query from redis", slink, "OK")
    return redisRet.oriUrl
  }

  const queryResult = await mysqlClint
    .getRepository(Link)
    .createQueryBuilder("link")
    .where("link.slink = :id", { id: slink })
    .getOne()

  const tmpRedis: SlinkRedisInterface = {
    //
    oriUrl: queryResult?.oriUrl || "",
    slink: slink,
    isDelete: queryResult?.isDelete
  }
  console.debug("query from mysql", slink, JSON.stringify(queryResult))
  const isRedisOk = setSlink(slink, tmpRedis, !queryResult?.oriUrl) // 如果查询到了，按照默认有效期缓存起来，否则短缓存
  console.debug("query set redis", slink, isRedisOk)
  if (!isRedisOk) {
    // TODO 解决数据一致性问题，存入mysql的表里，然后做定时任务，同步到redis里面；或者走消息队列
  }

  if (queryResult && queryResult.oriUrl) {
    console.debug("query ", slink, queryResult.oriUrl)
    return queryResult.oriUrl
  }
  return Promise.reject("没有找到")
}

export async function add(appid: string, oriUrl: string): Promise<string> {
  let tryTimes = -1
  let slink = ""
  let isOk = false
  while (tryTimes < 5) {
    // 重试5次
    tryTimes++
    slink = await getHashId()
    console.debug("add try", slink, tryTimes)
    try {
      const queryResult = await mysqlClint
        .createQueryBuilder()
        .insert()
        .into(Link)
        .values([{ appid, oriUrl, slink }])
        .execute()

      console.debug("add insert mysql", slink, queryResult?.raw?.affectedRows)
      if (queryResult?.raw?.affectedRows) {
        isOk = true
        break
      }
    } catch (error) {
      console.error(error)
      return Promise.reject("数据错误")
    } finally {
    }
  }
  if (isOk) {
    // 写入成功同步到redis里面
    const tmpRedis: SlinkRedisInterface = {
      oriUrl: oriUrl,
      slink: slink,
      isCollision: tryTimes
    }
    setSlink(slink, tmpRedis)
  }
  console.debug(`add result: slink[${slink}],tryTimes[${tryTimes}]`)
  return isOk ? slink : Promise.reject("添加失败")
}

export async function update(appid: string, oriUrl: string, slink: string): Promise<string> {
  let isOk = false
  // if (isShortId(slink)) {
  //   return Promise.reject("不合法的短链")
  // }

  // const redisRet: SlinkRedisInterface = await getSlink(slink)
  // if (!redisRet?.oriUrl) {
  //   return Promise.reject("不存在的短链")
  // }

  const queryResult = await mysqlClint
    .createQueryBuilder()
    .update(Link)
    .set({ oriUrl })
    .where("appid = :appid AND slink = :slink", { appid, slink })
    .execute()

  if (queryResult?.affected || queryResult?.affected === 0) {
    isOk = true
  }

  if (isOk) {
    // 写入成功同步到redis里面
    const tmpRedis: SlinkRedisInterface = {
      oriUrl: oriUrl,
      slink: slink
    }
    setSlink(slink, tmpRedis)
  }

  return isOk ? slink : Promise.reject("修改失败")
}
