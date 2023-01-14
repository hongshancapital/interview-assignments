import express from 'express'
import { GetUniqID } from './generator'
import { ConvertToBase62String } from './util'
import { DBSet, DBGet } from './database'

const short_url_server = express()
const port = 8080

short_url_server.use(express.json())

short_url_server.post("/api/create_short_url", (req, rsp) => {
    var long_url:string = req.body.long_url
    var uniq_id:number = GetUniqID()
    var base62_str:string = ConvertToBase62String(uniq_id)
    console.debug(`DBG: long url ${long_url} short url ${base62_str}`)
    DBSet(base62_str, long_url)
    rsp.json({short_url: base62_str})
})

short_url_server.get("/api/query_long_url/:short_url", (req, rsp) => {
    var short_url:string = req.params.short_url
    var long_url:string = ""
    if (short_url.length > 0) {
        long_url = DBGet(short_url)
    }
    console.debug(`DBG: short url ${short_url} long url ${long_url}`)
    rsp.json({long_url: long_url})
})

short_url_server.listen(port, () => {
    console.log(`INFO: Short url server start at port ${port}`);
})
