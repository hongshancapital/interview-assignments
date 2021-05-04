import express from 'express';
import bodyParser from 'body-parser';
import { registerUser } from './data/users';

const app = express();
const PORT = 3000;

app.use(bodyParser.urlencoded({ extended: false }));

app.get('/', (req, res) => {
  res.send('Hello world');
});

app.post('/api/register', (req, res) => {
    const { username, password,repeat_password } = req.body;
    if (!username?.trim() || !password?.trim()) {
      return res.status(400).send('Bad username or password');
    }
    if(password?.trim()!=repeat_password?.trim()){
      return res.status(400).send(' repeat password is error');
    }
    //验证用户名
    let checkUserName = /^[A-Za-z_][\w]+/i.test(username);
    if(false==checkUserName){
      return res.status(400).send('user name check is fail');
    }
    //验证密码长度
    if(password.length<=6){
      return res.status(400).send('password length min is > 6 ');
    }
    //验证密码规则
    let checkPassword = /\d{3}/.test(password);
    if(false==checkPassword){
      return res.status(400).send('password check is fail');
    }
    //注册账户
    registerUser({ username, password,repeat_password });
    res.status(201).send('User created');
  });

app.listen(PORT, () => {
  console.log('Express with Typescript! http://localhost:${PORT}');
});