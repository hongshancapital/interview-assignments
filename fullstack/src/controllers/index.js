module.exports = (app) => {
  // 打印一个hello
  app.get('/', (req, res) => {
    res.send('hello')
  })

}
