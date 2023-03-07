import express from 'express'
import routers from './routers'

const app: express.Application = express()
const port: number = Number(process.env.PORT) || 8080

app.use(routers)

app.listen(port, () => console.log(`port: ${port}`))

export default app
