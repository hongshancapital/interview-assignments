import express, { Request, Response } from "express"
import { resolve } from "path"
import yaml from "yamljs"
import bodyParser from "body-parser"
import shortId from "shortid"
import moment from "moment"
import joi from "joi"
import rateLimit from "express-rate-limit"
import knexInstance from "../config/database/database"
const knex = knexInstance()
const limiter = rateLimit({
    max: 1,
    windowMs: 1000,
    message: "Too many request from this IP"
});

const { webSite: { port } } = yaml.load(resolve(__dirname, `../config/${process.env.NODE_ENV || 'development'}/config.yml`))
const app = express()

app.use(bodyParser.json())

app.post("/shortUrl", limiter, async (req: Request, res: Response) => {
    const schema = joi.object({
        longUrl: joi.string().required().error(new Error('缺失longUrl参数'))
    })
    try {
        await schema.validateAsync(req.body, {allowUnknown: true, abortEarly: true})
        let { longUrl } = req.body
        let shortid = shortId.generate()
        if (shortid.length > 8) shortid = shortid.slice(0, 8)
        await knex('url_map').insert({ shortUrl: shortid, longUrl: longUrl, created_at: moment() })
        res.json({ shortUrl: shortid })
    } catch (e) {
        res.status(400)
        res.json({'message': e.message})
    }
})

app.get("/:shortUrl", async (req: Request, res: Response) => {
    let { shortUrl } = req.params
    let urlList = await knex('url_map').where('shortUrl', shortUrl).select()
    res.json(urlList)
})

app.listen(port, () => {
    console.log(`\x1B[31m[servers] server is starting http://localhost:${port}\x1B[0m`);
})