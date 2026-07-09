import log4js from "log4js";
import { MysqlError } from 'mysql';
import { HostData } from '../type/hostResponse'
import sqlClient from '../config/mysql'
import redisClient from '../config/redis'

const logger = log4js.getLogger("app")
/**
 * 单例模式
 * 类似springboot的bean管理
 */
class HostService {
  constructor() {
    console.log("HostService 初始化")
  }
  async save(longHost: string): Promise<HostData> {
    logger.info("HostService save longHost:", longHost)
    // 查询库中是否有此记录
    let hostMap: HostMap = await sqlClient.selectByLongHost(longHost)
    // 已存在记录
    if (hostMap != null) {
      // 并发情况
      if (hostMap.host_short == undefined || hostMap.host_short == null || hostMap.host_short == "") {
        return {
          longHost: longHost,
          shortHost: this.encode10To62(hostMap.id)
        }
      }
      return {
        longHost: longHost,
        shortHost: hostMap.host_short
      }
    }
    try {
      // 不存在记录
      let setNxResult: boolean = await redisClient.setNx(longHost, 1, 60)
      // 加锁失败，有任务正在运行
      if (!setNxResult) {
        return null;
      }
      // 加锁成功
      let id: number = await sqlClient.insert({ host_long: longHost })
      if (id == undefined || id == null) {
        return null;
      }
      // 插入成功
      let shortHost: string = this.encode10To62(id)
      await sqlClient.updateShortHost({
        host_short: shortHost,
        id: id
      })
      return {
        longHost,
        shortHost
      }
    } catch (e) {
      logger.error(e)
    } finally {
      // 最终解锁
      redisClient.del(longHost)
    }
  }

  async search(shortHost: string): Promise<HostData>  {
    logger.info("HostService search")
    let hostMap: HostMap = await sqlClient.selectByShortHost(shortHost)
      // 并发情况
      if (hostMap != null && hostMap.host_long != undefined && hostMap.host_long != null && hostMap.host_long != "") {
        return {
          longHost: hostMap.host_long,
          shortHost: shortHost
        }
      }
    return null
  }

  /**
   * 26小写字母+26大写字母+10数字 = 62
   * @param decimal 
   * @returns 
   */
  private encode10To62(decimal: number): string {
    if (decimal < 0) {
      return null;
    }
    // 秘钥最好放在一个有安全保证的平台
    const SECRET: string = "KL01234ABCDEFGHIJ56789MNOPcdefghijklmnopQRSTUVWXYZabqrstuvwxyz";
    let result = "";
    while (decimal > SECRET.length - 1) {
      result += SECRET[(decimal % SECRET.length)]
      decimal /= decimal
    }
    result += SECRET[decimal]
    return result.split("").reverse().join("");
  }
}
export default new HostService()