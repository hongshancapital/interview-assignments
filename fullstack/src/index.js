const fs = require('fs')
const path = require('path')
spaces.bus = require('./utils/bus.js')
spaces.modules = new Map()


module.exports = function * () {
  // 这里定义为function * 或者 async 函数，因为可以处理某些异步
  fs.readdirSync(path.resolve(__dirname, 'core')).forEach(file => {
    const Klass = require(`./core/${file}`)
    spaces.modules.set(path.basename(file, '.js'), new Klass())
  })
}
