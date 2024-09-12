import mongoose from 'mongoose';

// 本人自己服务器
const DB_HOST = 'mongodb://admin:admin@1.116.229.2:27017/short-url?authSource=admin';
 
mongoose.connect(DB_HOST);
 
mongoose.connection.on('connected', () => {
    console.log('连接成功mongodb')
});
 
mongoose.connection.on('error', function (err) {
    console.log('Mongoose connection error: ' + err, '连接错误');
});
 
mongoose.connection.on('disconnected', function () {
    console.log('Mongoose connection disconnected', '连接断开');
});


