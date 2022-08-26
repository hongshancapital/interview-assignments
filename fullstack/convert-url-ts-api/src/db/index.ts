
import { Sequelize } from "sequelize-typescript";
import path from "path";
const sequelize = new Sequelize('test001' as string,'test001' as string,'test001' as string,{
    host:'localhost',
    dialect:'mysql',
    port:3306,
    models:[path.join(__dirname,'../dto/model/*.ts')]
})
const db = async () =>{

try{
    await sequelize.authenticate();
    console.log("数据看链接成功！");
}catch(e){
    console.log(e)
}
}
export default db;