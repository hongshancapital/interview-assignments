/*
 * @Author: 马俊鸿 zxmajunhong@forxmail.com
 * @Date: 2022-04-24 21:35:56
 * @LastEditTime: 2022-04-24 21:45:30
 * @Description: craco的配置文件，craco的使用说明见https://github.com/gsoft-inc/craco
 */

const path = require('path');
const resolve = dir => path.resolve(__dirname, dir);

module.exports = {
  webpack: {
    alias: {
      '@': resolve('src'),
    }
  }
}