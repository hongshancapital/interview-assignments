/*
  mvc架构，加载controler、module
*/

module.exports = (app) => {
  require('./routes')(app)
  app.use(Config.restApiRoot, require('./load-models.js'))
}
