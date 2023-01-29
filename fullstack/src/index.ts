import * as express from "express"
import * as bodyParser from "body-parser"
import { Request, Response } from "express"
import { AppDataSource } from "./data-source"
import { Routes } from "./routes"

const init = () => {
    AppDataSource.initialize().then(async () => {

        // create express app
        const app = express()
        app.use(bodyParser.json())
    
        // router regist, easy to expand.
        Routes.forEach(route => {
            (app as any)[route.method](route.route, (req: Request, res: Response, next: Function) => {
                const result = (new (route.controller as any))[route.action](req, res, next)
                if (result instanceof Promise) {
                    result.then(result => result !== null && result !== undefined ? res.send(result) : undefined)
                } else if (result !== null && result !== undefined) {
                    res.json(result)
                }
            })
        })
    
    }).catch(error => console.log)
}

init()
export default init
