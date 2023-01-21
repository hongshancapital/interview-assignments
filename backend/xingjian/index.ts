import express from 'express'
import { GetUniqID } from './generator'
import { ConvertToBase62String } from './util'
import { GetRecordeByShortUrl, GetRecordeByLongUrl, AddRecord } from './database'
import { ShortUrlInfo } from './entity/shorturlinfo'

const MAX_ID:number = 1000000000;

const short_url_server = express()
const port = 8080

short_url_server.use(express.json())

short_url_server.post("/api/create_short_url", async (req, rsp) => {
    var long_url:string = req.body.long_url
    if (long_url.length == 0) {
        return rsp.json({short_url: ""})
    }
    const url_info:ShortUrlInfo = await GetRecordeByLongUrl(long_url)
    if (url_info != null && url_info.short_url.length > 0) {
        rsp.json({short_url: url_info.short_url})
    }

    var base62_str:string = ""
    /*
     * 2023.01.21 update
     * 1.目前已经优化了GetUniqID调用，保证了每次生成的id都是全局唯一的，因此通过base62生成的short_url也必然不会重复;
     * 2.但是目前我还是保留了这个while逻辑，因为生产环境往往会出现一些无法预知的问题，例如我们依赖的上游id生成服务出问题了，出现了重复的id，这个时候最好还是有一个兜底;
     * 3.正常情况下这里的复杂度已经是O(1)，当然这里也要避免死循环(异常情况)，所以对循环也做了一些优化，循环超过10次强制跳出循环并告警;
     */
    let count:number = 0;
    while (true) {

        base62_str = ""
        if (count++ > 10) {
            // 这里是一种异常情况，可以根据实际项目添加一些日志或者是告警，提醒开发人员出现了异常;
            // some warning logic
            console.log("ERR: Generate id error try count", count, long_url)
            break
        }

        /*
         * 1.碰撞处理，避免生成的short url已经存在;
         * 2.理论上来说只要GetUniqID获取的id是全局唯一的，那这里生成的base62_str就不会重复，这里的复杂度便是O(1);
         */
        let uniq_id:number = GetUniqID()
        if (uniq_id > MAX_ID) {
            // 为了保证short_url长度不超过8，这里的uniq_id不能大于62^8 - 1，js的number无法表示，这里MAX_ID暂时取10^9
            // some warning logic
            console.log("ERR: Generate id error id ", uniq_id, long_url)
            break
        }

        base62_str = ConvertToBase62String(uniq_id)
        const tmp_info:ShortUrlInfo = await GetRecordeByShortUrl(base62_str)
        if (tmp_info != null && tmp_info.short_url.length > 0) {
            continue
        }

        count = 0
        var new_url_info:ShortUrlInfo = new ShortUrlInfo()
        new_url_info.short_url = base62_str
        new_url_info.long_url = long_url
        AddRecord(new_url_info)
        break
    }
    rsp.json({short_url: base62_str})
})

short_url_server.get("/api/query_long_url/:short_url", async (req, rsp) => {
    var short_url:string = req.params.short_url
    var long_url:string = ""
    if (short_url.length > 0) {
        const url_info:ShortUrlInfo = await GetRecordeByShortUrl(short_url)
        if (url_info != null && url_info.long_url.length > 0) {
            long_url = url_info.long_url
        }
    }
    rsp.json({long_url: long_url})
})

short_url_server.listen(port, () => {
    console.log(`INFO: Short url server start at port ${port}`);
})
