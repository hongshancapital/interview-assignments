import * as express from 'express';
import { connectDB } from './models';
import urlHandler from './routers/url';
import shortenCode from './routers/shortenCode';

const app = express();
app.use(express.json()); 
app.use(express.urlencoded({ extended: true })); 

// 连接MongoDB
connectDB();
// 路由设置
app.use('/', shortenCode);
app.use('/url', urlHandler);

const port = 50501;
app.listen(port, () => {
  console.log(`Express Server running on port: ${port}`);
});