import { type } from "os"
import { DataSource, DataSourceOptions, createConnection } from "typeorm"
import { mysql as mysqlConfig } from "../config"
const preOccList: number[] = [] // 预占自增id
type STATUS = "INIT" | "CONNECTED" | "CONNECTING" | "ERROR"
let mysqlStatus: STATUS = "INIT"
import { LinkShadow } from "../entity/linkShadow"

const mysqlConnect: DataSourceOptions = {
  ...mysqlConfig.PoolMainDbConnect,
  ...{
    type: "mysql",
    synchronize: false
  }
}


const dbMainDataSource = new DataSource(mysqlConnect)


async function preOccupation(){
  if(preOccList.length > 10){
    return 
  }
  const values = []
  for(let i = 0 ;i < 100; i ++){
    values.push({})
  }
  const insertResult = await dbMainDataSource
  .createQueryBuilder()
  .insert()
  .into(LinkShadow)
  .values(values)
  .execute()
  // console.debug("preOccupation insert mysql",insertResult,  insertResult?.raw?.affectedRows)
  insertResult?.identifiers.forEach((item) => {
    preOccList.push(item.id)
  })
}


dbMainDataSource
  .initialize()
  .then(() => {
    preOccupation()
    mysqlStatus = "CONNECTED"
    console.log("Mysql Connected ............  [OK] ")
    
  })
  .catch((err) => {
    console.error(err)
    mysqlStatus = "ERROR"
    throw new Error("Error during Data Source initialization:")
  })

async function getOnePreOccId(): Promise<number>{
  if(preOccList.length < 2){
    await preOccupation() 
  }
  const preOccId = preOccList.shift()
  if (!preOccId) {
    return Promise.reject('没有预占ID，请检查数据库')
  }
  return Promise.resolve(preOccId)
}

export default dbMainDataSource
export { mysqlStatus, getOnePreOccId }
