import express from 'express';
import { router } from './route';
import * as dotenv from 'dotenv'
import { connect } from 'mongoose';

const app:express.Application = express();
const PORT:number = 3000;
dotenv.config()

app.use(express.json());
// 加载业务router
app.use('/',router) 
const start = async ()=>{
    const mongoURI = process.env.mongoURI||''
    await connect(mongoURI); 
    console.log('db connection established')
    app.listen(PORT, () => {
        console.log(`short url service started listening : ${PORT}`);
    });
}
start();