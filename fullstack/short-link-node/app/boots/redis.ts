import { runMain } from "module"
import { createClient } from "redis"
import redisConfig from "../config/redis"
const connectConfig = redisConfig.RedisMainDbConnect
const redisUrl = `redis://${connectConfig.url}:${connectConfig.port}/${connectConfig.db}`

type STATUS = "INIT" | "CONNECTED" | "CONNECTING" | "ERROR"
let redisStatus: STATUS = "INIT"
console.log("Redis Connecting ... ", redisUrl)
const redisClient = createClient({ url: redisUrl })
redisClient.on("error", (err) => {
  console.error(err)
  redisStatus = "ERROR"
  throw new Error("Error during Redis initialization:")
  // TODO 切换备机
})

redisClient.on("connect", () => {
  console.log("Redis Connected ............  [OK]")
  redisStatus = "CONNECTED"
})

redisClient.on("end", () => {
  console.log("Redis end ...")
  redisStatus = "ERROR"
})

redisClient.connect()

export default redisClient
export { redisStatus }
