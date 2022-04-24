/* istanbul ignore file */
import * as express from "express"
import * as bodyParser from "body-parser"
import { Request, Response } from "express"
import { AppDataSource } from "./data-source"
import { Routes } from "./routes"
import { env } from "../env"
import { createClient } from "redis"
export const client = createClient()
client.on("error", (err) => {
	console.log("Error " + err)
})
export const bootstrap = async () => {
	try {
		return AppDataSource.initialize().then(async (res) => {
			// create express app
			const app = express()
			app.use(bodyParser.json())

			// register express routes from defined application routes
			Routes.forEach((route) => {
				;(app as any)[route.method](
					route.route,
					(req: Request, res: Response, next: Function) => {
						const result = new (route.controller as any)()[
							route.action
						](req, res, next)
						if (result instanceof Promise) {
							result.then((result) =>
								result !== null && result !== undefined
									? res.send(result)
									: undefined
							)
						} else if (result !== null && result !== undefined) {
							res.json(result)
						}
					}
				)
			})

			// setup express app here
			// ...
			// 统一处理错误
			app.use((err: any, req: Request, res: Response, next: Function) => {
				res.status(200)
				res.json({
					error: 500,
					message: err.message,
				})
			})
			// start express server
			const server = app.listen(env.port)

			try {
				await client.connect()
				// await client.bf.reserve('shortUrl', 0.01, 1000);
			} catch (e) {
				console.log("redis 连接失败", e)
			}

			console.log(
				"Express server has started on port 3000. Open http://localhost:3000 to see results"
			)
			return { app, server, dataSource: res, client }
		})
	} catch (err: any) {}
}
