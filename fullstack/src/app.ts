import express from 'express'
import config from 'config'
import routes from './routes'
import initMiddleware from './middleware'
import {logger} from '../logs/log4j.config'
import {connectDB} from "../config/db";

const app = express()

connectDB()

initMiddleware(app)

const PORT = config.get<number>('server.port')

// 启动
app.listen(PORT, async () => {
    logger.info(`App is running on PORT ${PORT}`)
    routes(app)
})

export default app;
