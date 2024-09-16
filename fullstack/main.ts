import express, { Request, Response } from "express";
import { shortToLong, longToShort } from './shortUrl';

// console.log('main.ts');
const app = express();

// 返回长链接
app.get("/shortToLong", (req: Request, res: Response) => {
  var result: string = shortToLong(<string>req.query.url);
  res.json({ result });
});
// 返回短链接
app.get("/longToShort", (req: Request, res: Response) => {
  var result: string = longToShort(<string>req.query.url);
  res.json({ result });
});

app.listen(3000);

