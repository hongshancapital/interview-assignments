import express, { Express, NextFunction, Request, Response } from "express";
import dotenv from "dotenv";
import { getUrl, generateUrl } from "./service";
import bodyParser from "body-parser";
import cors from "cors";
import { StatusCodes } from "http-status-codes";

dotenv.config();

const app: Express = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded());

// TODO:logs sentry security
app.use((err: Error, _: Request, res: Response, __: NextFunction) => {
  const status = StatusCodes.INTERNAL_SERVER_ERROR;
  return res.status(status).json({
    error: err.message,
  });
});

app.use(cors());

// 跳转
app.get("/:code", async (req: Request, res: Response) => {
  if (req.params.code) {
    const url = await getUrl(req.params.code);
    return res.redirect(302, url);
  }
  return res.send("Welcome!");
});

// 返回短链接信息
app.get("/api/:code", async (req: Request, res: Response) => {
  if (req.params.code) {
    const url = await getUrl(req.params.code);
    return res.send(url);
  }
  return res.send("Welcome!");
});

// 生成并返还短链接信息
app.post("/api/generate", async (req: Request, res: Response) => {
  if (req.body.longUrl) {
    const data = await generateUrl(req.body.longUrl);
    return res.send(data);
  }
  res.send("Some error");
});

export default app;
