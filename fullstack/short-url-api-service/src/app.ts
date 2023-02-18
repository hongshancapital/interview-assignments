import { isProduction } from "./app-config"
import { AppDataSource } from "./data-source"
import express, { Express, Request, Response } from "express"
import shortUrlRouter from "./modules/short-url/short-url-router";
import ServerResponse from "./responses/server-response"

const app: Express = express()

console.log("start connecting postgres database")
AppDataSource.initialize()
    .then(async () => {
        console.log("connected database")
        startApp()
    })
    .catch((error) => console.log(error))

const startApp = () => {

    app.use(express.json())
    app.use(express.urlencoded({ extended: false }))

    registerRoutes(app)

    // catch 404 and forward to error handler
    app.use(function (_req, res, _next) {
        res.status(404).json(
            ServerResponse.UnimplementedMethod(),
        );
    })

    // error handler
    app.use(function (err: any, req: Request, res: Response, _next: any) {
        console.error(err)
        res.status(err.status || 500).send({
            message: err.message,
            error: isProduction ? {} : err.stack ?? Object.assign({}, err),
        })
    })

    const port = process.env.PORT || "3000"

    app.listen(port, () => {
        console.log(
            `⚡️[server]: Server is running at http://localhost:${port}`,
        )
        app.emit('listened')
    })
}


const registerRoutes = (app: Express) => {
    app.use('/short-url', shortUrlRouter);
}

export default app

