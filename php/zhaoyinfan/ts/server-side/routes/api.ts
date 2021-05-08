import { NextFunction } from "express";
import { getUserInfo, checkUser } from "../controller/user";
var express = require('express');
var router = express.Router();

/**
 * 注册账号接口
 * Author zhaoyinfan
 * Date 2021-05-07
 */
router.post('/register', function (req: any, res: any, next: NextFunction) {

  let { username, password, repeat_password } = req.body;
  let checkUserPwd = checkUser(username, password, repeat_password);
  if (checkUserPwd.code == 1001) {
    console.log('注册用户存在');
    res.send(checkUserPwd);
  }
  let userIsExsit = getUserInfo(username);
  if (userIsExsit.code == 1000) {
    console.log('注册用户存在');
    res.send(userIsExsit);
  }
  console.log('新注册用户');
  res.send(userIsExsit);
});
module.exports = router;
