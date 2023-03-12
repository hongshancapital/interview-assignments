/*
 * @Author: zhangyan
 * @Date: 2023-03-09 01:58:11
 * @LastEditTime: 2023-03-11 00:45:10
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/app.ts
 * @Description: express相关定义
 */
import express, { Request, Response } from "express";
import index from "./src/routes/index";
import getUrl from "./src/routes/getUrl";
import setUrl from "./src/routes/setUrl";
import bodyParser from "body-parser";

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.urlencoded({ extended: false }));
app.use(express.json());
app.use("/", index);
app.use("/get_url", getUrl);
app.use("/set_url", setUrl);

app.get("/", (_req: Request, res: Response) => {
  res.send("Welcome");
});

export default app;
