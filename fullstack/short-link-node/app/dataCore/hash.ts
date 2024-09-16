// import shortId from "shortid"
// const shortid = require("shortid")
import { getOnePreOccId } from "../boots/mysql"
import BASHCOFNIG  from '../config'
const CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-"
const N_decimal = CHARS.length

// shortid.characters(CHARS)
async function getHashId(): Promise<string> {
  // return shortid.generate()
  const id: number = await getOnePreOccId()
  let t = id
  let retStr = ''
  while(t > 1){
    let mod = t % N_decimal
    t = Math.floor(t / N_decimal)
    retStr = retStr + CHARS[mod]
  }
  let flag = 0
  flag = flag | retStr.length  // 计算标志位，hash长度
  if (BASHCOFNIG.TABLE_Flag) { // 计算标志位，分表标示
    flag = flag | (1 << 4)
    retStr = BASHCOFNIG.TABLE_Flag + retStr
  }
  if (BASHCOFNIG.DB_Flag) { // 计算标志位，分库标示
    flag = flag | (1 << 5)
    retStr = BASHCOFNIG.DB_Flag + retStr
  }
  retStr = retStr + CHARS[flag]
  return Promise.resolve(retStr)
}
function isShortId(id: string) {
  // return shortid.isValid(id)
  // TODO
  return true
}



export { getHashId, isShortId }
