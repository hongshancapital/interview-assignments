import express from "express";
import mongoose from "mongoose"
import * as bodyParser from "body-parser"
import * as dotenv from 'dotenv'
import morgan from "morgan"
import helmet from "helmet"

import {shortenUrl, queryUrlById,} from "./app/controller"

// env variables
dotenv.config()

const db_url = process.env.DB_URL || "mongodb://127.0.0.1:27017"
mongoose.set('strictQuery', false);
mongoose.connect(db_url).then(() => {
    // console.log('Connected successfully');
}).catch((err) => {
    console.log("Error received: " + err)
})

const app = express()
app.use(helmet())
app.use(bodyParser.urlencoded({extended: true}))
app.use('/api/*', bodyParser.json())
app.use(morgan('combined'))
app.set('port', process.env.PORT || 3000)
app.post('/api/shorten', shortenUrl)
app.get('/api/shorten/:shortId', queryUrlById)
app.listen(app.get("port"), () => {
    console.log('listening on %d', app.get('port'))
})
