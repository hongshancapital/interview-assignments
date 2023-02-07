import mysqlConfig from "./mysql"
import redisConfig from "./redis"
import baseConfig from "./base"
const env = process.env.NODE_ENV

let base = baseConfig
let mysql = mysqlConfig
let redis = redisConfig

if (env === "dev" || env === "development") {
  base = Object.assign(base, {})
  mysql = Object.assign(mysql, {})
  redis = Object.assign(redis, {})
} else if (env === "test" || env === "testing") {
  base = Object.assign(base, {})
  mysql = Object.assign(mysql, {})
  redis = Object.assign(redis, {})
}

export default base
export { mysql, redis }
