/*
 * @Author: Json Chen
 * @Date: 2023-02-13 16:36:25
 * @LastEditors: Json Chen
 * @LastEditTime: 2023-02-13 19:13:02
 * @FilePath: /interview-assignments/fullstack/src/app.ts
 */
import express, { Application } from 'express';
import { connect } from './connections/mongo';
import router from './routers';
const app: Application = express();

app.use(express.json());

connect();

app.use(router)

app.listen(3000, () => console.log('Listening on 3000'));