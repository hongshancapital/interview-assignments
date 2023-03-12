/*
 * @Author: zhangyan
 * @Date: 2023-03-10 16:22:51
 * @LastEditTime: 2023-03-10 16:23:17
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/jest.config.js
 * @Description: 
 */
module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
  moduleNameMapper: {
    "@fullstack/(.*)": "<rootDir>/src/$1"
  },
};