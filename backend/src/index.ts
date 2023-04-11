import express from 'express';
import { router } from './route';
import { connect } from 'mongoose';

const app:express.Application = express();
const PORT:number = 3000;

app.use(express.json());
app.use('/',router)
const start = async ()=>{
    await connect(
        "mongodb://test:test@localhost:27017/playground?authSource=playground"
    ); 
    console.log('db connected')
    app.listen(PORT, () => {
        console.log(`short url service started http://localhost:${PORT}`);
    });
}
start();