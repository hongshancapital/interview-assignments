import express, { Application, Request, Response, NextFunction } from "express";
import bodyParser from "body-parser";

import { router as shortUrlRouter } from './routes/shortUrl.router';

const app: Application = express();
app.use(bodyParser.json());

app.use("/shortUrl", shortUrlRouter);

app.use("/", (req: Request, res: Response, next: NextFunction): void => {
  res.json({ message: "Allo! Catch-all route." });
});

export default app;