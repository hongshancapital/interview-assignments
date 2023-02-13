module.exports = () => {
    const mongoose = require('mongoose')
    mongoose.set('strictQuery', false)
    mongoose.connect('mongodb://127.0.0.1:27017/urlMap',(err:any)=>{
            if (err) {
                console.log(err);    
            }else{
                console.log("数据库连接成功");
            }
    })
}