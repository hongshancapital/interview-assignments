import express from "express";
import mongoose from "mongoose";
import bluebird from "bluebird";
import 'dotenv/config'

const {MONGODB_URI_LOCAL,MONGODB_URI,PORT,NODE_ENV} = process.env;
const env: string =  NODE_ENV || 'dev';
const port: string | number = PORT || 3000;

import shortDomainController from "./controllers/shortDomainController";

// Create Express server
const app = express();

// Connect to MongoDB
const mongoUrl = env !=='prod' ? MONGODB_URI_LOCAL: MONGODB_URI;
mongoose.Promise = bluebird;
mongoose.connect(mongoUrl, { useNewUrlParser: true, useCreateIndex: true, useUnifiedTopology: true } ).then(
    () => { /** ready to use. The `mongoose.connect()` promise resolves to undefined. */ },
).catch(err => {
    console.log(`MongoDB connection error. Please make sure MongoDB is running. ${err}`);
    // process.exit();
});

app.set("port", port || 3000);
app.use(express.static('public'));

// 2 api 接口
app.get('/api/long/to/short',shortDomainController.longToShort);
app.get('/api/short/to/long',shortDomainController.shortToLong);

// 404
app.use(function(req: express.Request, res: express.Response, next) {
    let err = new Error("Not Found");
    next(err);
});

// 500
app.use(function(err: any, req: express.Request, res: express.Response, next: express.NextFunction) {
    res.status(err.status || 500);
    res.json({
        code: 1,
        error: {},
        message: err.message || '服务器好像出了点问题...稍后再试试'
    });
});

export default app;
