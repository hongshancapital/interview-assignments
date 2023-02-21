import express, { Request, Router, Express } from "express";
import { RequestHandler, Response } from "express-serve-static-core";
import router from "./routes";
import { defaultApiVersion } from "./config/config";
import { NotFoundError } from "./utils/appError";
import middlewares from "./middlewares";

const cors = require("cors");

export const app: Express = express();

// configure app to use bodyParser
app.use(express.urlencoded({ extended: true }) as unknown as RequestHandler);
app.use(express.json() as unknown as RequestHandler);

// use cors middleware for diff client access host
app.use(cors());

// send index.html file on root request
app.use(express.static("dist") as unknown as RequestHandler);
app.get("/", (req: Request, res: Response) => {
  res.sendFile("/dist/index.html");
});

// all api routes will be prefixed with /api/v1
const routes: Router[] = Object.values(router);
app.use(`/api/${defaultApiVersion}`, routes);

//use not found middleware
app.use((_req, _res, next) => next?.(new NotFoundError()));

//middleware to use for all requests
app.use(Object.values(middlewares) as unknown as RequestHandler);

const port: number = Number(process.env.PORT) || 8888;
app.set("port", port);
