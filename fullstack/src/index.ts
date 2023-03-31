import express, {Request, Response} from "express";
import ShortUrl from "./services/ShortUrl"
import {env} from "./helper"

export const app = express()

app.get('/api/shorturl/:short', (req: Request, res: Response) => {
    ShortUrl.get(`${req.params.short}`.trim()).then((data) => {
        return res.json({
            status: true,
            info: "success",
            data: {
                url: data.url
            }
        })
    }).catch((_) => {
        return res.json({
            status: false,
            info: "Invalid short url",
            data: {url: ""}
        })
    })
})

app.post('/api/shorturl', [express.json()], (req: Request, res: Response) => {
    if (req.body.url === undefined || typeof req.body.url !== 'string' || req.body.url.trim() === "") {
        return res.json({
            status: false,
            info: "Invalid url",
            data: {url: ""}
        })
    }

    ShortUrl.create(req.body.url.trim()).then((data) => {
        return res.json({
            status: true,
            info: "success",
            data: {
                url: ShortUrl.completeUrl(data.short),
                short: data.short
            }
        })
    }).catch((_) => {
        return res.json({
            status: false,
            info: "Create short url failed",
            data: {url: ""}
        })
    })
})

const port = env('HOST_PORT', 80)
app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
})
