import express from 'express'
import router from './routes/index'

const app = express()
const port = process.env.PORT || '3000'

app.use(express.urlencoded({ extended: true }))
app.use(router)

app.listen(port)
