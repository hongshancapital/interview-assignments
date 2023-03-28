import express, { Express, Request, Response, NextFunction } from "express";
import appRouters from "./api/routes";
import { isValidURL } from "./util/util";

const app: Express = express();

const bodyValidator = (req: Request, res: Response, next: NextFunction) => {
  if (!req.body || !req.body.url) {
    res.status(400).send({
      msg: "please offer an url in your request body",
    });
  } else {
    next();
  }
};

const urlValidator = (req: Request, res: Response, next: NextFunction) => {
  if (!isValidURL(req.body.url)) {
    res.status(400).send({
      msg: "invalid url",
    });
  } else {
    next();
  }
};

app.use(express.json());
app.use(bodyValidator, urlValidator);
app.use("/", appRouters);

export default app;
