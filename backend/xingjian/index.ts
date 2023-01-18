import express from 'express'
import { GetUniqID } from './generator'
import { ConvertToBase62String } from './util'
import { GetRecordeByShortUrl, GetRecordeByLongUrl, AddRecord } from './database'
import { ShortUrlInfo } from './entity/shorturlinfo'

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
    while (true) {
        /*
         * 1.碰撞处理，避免生成的short url已经存在;
         * 2.理论上来说只要GetUniqID获取的id是全局唯一的，那这里生成的base62_str就不会重复，但是目前并未保证这个，所以还是处理一下冲突;
         */
        var uniq_id:number = GetUniqID()
        base62_str = ConvertToBase62String(uniq_id)
        const tmp_info:ShortUrlInfo = await GetRecordeByShortUrl(base62_str)
        if (tmp_info != null && tmp_info.short_url.length > 0) {
            continue
        }
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
