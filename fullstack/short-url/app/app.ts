import express, { Application, Request, Response } from 'express'
import * as fnv from 'fnv-plus'
import bodyParser from "body-parser"

const app: Application = express();

// 测试服务使用map，后续需要持久化可替换为Redis或其他数据库
let map: Map<string, string> = new Map()

app.use(bodyParser.json())

app.post('/add', (req: Request, res: Response) => {
    let longUrl = req.body.url
    if (!longUrl) {
        res.send("The long url add error")
    } else {
        let shortUrl = fnv.fast1a32hex(longUrl)
        if (!map.has(shortUrl)) {
            map.set(shortUrl, longUrl)
        }

        res.send(shortUrl)
    }
});

app.get('/short/:url', (req: Request, res: Response) => {
    let shortUrl = req.params.url
    if (map.has(shortUrl)) {
        let longUrl = map.get(shortUrl)
        res.redirect(301, "http://localhost:7777/long/" + longUrl)
    } else {
        res.send("This short url not found")
    }

});

app.get("/long/:url", (req: Request, res: Response) => {
    res.send("This is real long url:" + req.params.url)
})

app.listen(7777, function () {
    console.log('App listening on port 7777!')
})