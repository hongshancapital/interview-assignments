global.Promise = require('bluebird')
global.ROOTPATH = __dirname
global._ = require('lodash')
global.Config = require('./config/config.json')
global.logger = new (require('./src/lib/log'))('log')
global.spaces = { network: {} }
global.db = {
  redis: require('./src/db/redis')
}
const co = require('co')


function catcher (e) {
  // 如果是pm2启动这里会有错误日志
  console.error(e)
}

const index = require('./src')

function * main() {
  yield * index()
  setImmediate(() => {
    spaces.bus.message('running')
  })
}

co(main).catch(catcher)
