import { redisStatus } from "./redis"
import { mysqlStatus } from "./mysql"

function init() {}

function getState() {
  return {
    mysqlStatus,
    redisStatus
  }
}

export default init
export { getState }
