const express = require('express');
const rds = require('ali-rds');
// const bodyParser = require('body-parser');
const ejs = require('ejs');
const config = require('./config');

const app = express();

app.mysql = rds(config.mysql);

// 配置跨域
app.all('*', (req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Content-type', 'application/json;charset=utf-8');
  res.header('Access-Control-Allow-Headers', 'Content-Type');
  res.header('Access-Control-Allow-Methods', '*');
  next();
});

// 中间价机械json 配置了才可以使用req.body
// app.use(bodyParser.json());
app.use(express.json());
app.use("/", require('./routes/index')(app));
app.use("/api/url", require('./routes/url')(app));

app.engine('html', ejs.__express);
app.set('views', './views');
app.set('view engine', 'html');

app.use(express.static('./public'));

// 监听启动服务
const port = config.port;
app.listen(port, () => console.log('Server Listening on port ' + port));
