/*
  mvc架构，加载controler
*/

const fs = require('fs')
const path = require('path')

module.exports = (app) => {
  fs.readdirSync(path.resolve(__dirname, '..', 'controllers')).forEach(file => {
    if (typeof require(`../controllers/${path.basename(file, '.js')}`) === 'function') {
      require(`../controllers/${path.basename(file, '.js')}`)(app)
    }
  })
}
