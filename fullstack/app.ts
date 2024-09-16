import express from 'express';
import mongoose from 'mongoose';
import routers from './routers';
import bodyParser from 'body-parser';
const app: any = express();
mongoose.connect('mongodb://127.0.0.1:27017').then(()=>console.log('数据库连接成功！'));
//配置
app.use(express.urlencoded({ extended: true }));
app.use(bodyParser())
// 接口请求处理
app.use('/surl', routers);

app.listen(8000,(err:any):void=>{
    if (!err) {
        console.log('服务器连接成功');
    } else {
        console.log('服务器连接成功');
    };
})
export default app;