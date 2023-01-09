import { RedisClientType } from "redis"

import { Database } from "../db/index"
import MSG from "../model/json"
import { url2Base62 } from "./transit"
import { CURRENT_SEQ_NUM, RANGE, API } from "../constants"
import * as utils from "../lib/utils"
import logger from "../lib/logger"

export default class UrlShorten {
    private db: Database
    private redisCli: RedisClientType

    constructor(db: Database, redisCli: RedisClientType) {
        this.db = db
        this.redisCli = redisCli
    }

    public async initCache(): Promise<void> {
        // 获取当前处理 server 中当前发号序列号码。Redis 未初始化时向发号表新建一条发号段数据
        let currSeqNum = await this.redisCli.get(CURRENT_SEQ_NUM)
        if (currSeqNum == null) {
            this.setCurrNum()
        }
    }

    async setCurrNum(): Promise<number> {
        const result = await this.db.query(
            "select MAX(id) as MAX from sender_num_seq;"
        )

        const currSeqId = result ? result[0].MAX + 1 : 1
        // db 为读写分离，从 master 进行 insert/update 操作
        await this.db.query(
            "insert into sender_num_seq (startNum, endNum, createAt) values (?, ?, ?)",
            [currSeqId * RANGE + 1, (currSeqId + 1) * RANGE, new Date()],
            true
        )
        await this.redisCli.set(CURRENT_SEQ_NUM, currSeqId * RANGE + 1)
        return currSeqId * RANGE + 1
    }

    public async fetchLongByShort(shortUrl: string): Promise<MSG> {
        if (!utils.isValidShortUrl(shortUrl)) {
            return {
                Code: 1001,
                Text: API.ERROR.INVALID_SHORT_URL,
            }
        }

        const result = await this.db.query(
            "select longUrl from storage_url where shortUrl = (?) limit 1;",
            shortUrl
        )
        if (!result || result.length <= 0) {
            return {
                Code: 1101,
                Text: API.ERROR.LONG_URL_NOT_RETRIEVED,
            }
        }
        return {
            Code: 1000,
            Data: { shortUrl, longUrl: result[0].longUrl },
            Text: API.SUCCESS_RETRIEVE,
        }
    }

    public async shorten(longUrl: string): Promise<MSG> {
        if (!utils.isValidWS(longUrl)) {
            return {
                Code: 1002,
                Text: API.ERROR.INVALID_LONG_URL,
            }
        }

        let currSeqStr = await this.redisCli.get(CURRENT_SEQ_NUM)
        if (!currSeqStr) {
            logger.error(`1102 ${API.ERROR.INVALID_NUM_SEQ}`)
            return {
                Code: 1102,
                Text: API.ERROR.INVALID_NUM_SEQ,
            }
        }

        // int 是否考虑到高并发下的int溢出问题 ？？
        if (currSeqStr == null) {
            logger.error(`1103 ${API.ERROR.INVALID_HANDLE_SEQ}`)
            return {
                Code: 1103,
                Text: API.ERROR.INVALID_HANDLE_SEQ,
            }
        }
        // db 为读写分离，查询时默认从 slave 查询
        // 输入待作短链处理的长链是否已经进行过短链处理
        const exData = await this.db.query(
            "select shortUrl from storage_url where longUrl = (?)",
            longUrl
        )
        if (exData.length > 0) {
            return {
                Code: 1000,
                Data: { shortUrl: exData[0].shortUrl, longUrl },
                Text: API.SUCCESS_SHORTEN,
            }
        }
        // 10 进制转化为 62 进制
        let currSeqNum = parseInt(currSeqStr)
        const shortUrl = url2Base62(currSeqNum)

        // let data: StorageUrl = {
        //     shortUrl,
        //     longUrl,
        //     createAt: new Date(),
        // }
        await this.db.query(
            "insert into storage_url (shortUrl, longUrl, createAt) values (?, ?, ?)",
            [shortUrl, longUrl, new Date()],
            true
        )
        if (currSeqNum % RANGE === 0) {
            currSeqNum = await this.setCurrNum()
            await this.redisCli.set(CURRENT_SEQ_NUM, currSeqNum)
        } else {
            this.redisCli.incr(CURRENT_SEQ_NUM)
        }

        return {
            Code: 1000,
            Data: { shortUrl, longUrl },
            Text: API.SUCCESS_SHORTEN,
        }
    }
}
