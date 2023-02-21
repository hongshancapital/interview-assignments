import express, { Router, Request, Response } from 'express'
import cors from 'cors';
const bodyParser = require("body-parser")
const app = express()
const port = 7001
const Url = require('./models/url');
require('./plugins/db.ts')()
const router = require('./router/index')


app.use(cors());
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())
app.use(router);




app.listen(port, () => {
    console.log("The server runs on port:", port);
})

