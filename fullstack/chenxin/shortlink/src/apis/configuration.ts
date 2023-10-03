import { hooks, createConfiguration } from "@midwayjs/hooks";
import * as orm from "@midwayjs/orm";
import { join } from "path";
import bodyParser from "koa-bodyparser";
import * as dotenv from 'dotenv';

dotenv.config();
export default createConfiguration({
  imports: [
    orm, // 加载 orm 组件
    hooks({
      middleware: [bodyParser()],
    }),
  ],
  importConfigs: [join(__dirname, "./config/")],
});
