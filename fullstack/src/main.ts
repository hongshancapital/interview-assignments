import express, { Express, Request, Response } from 'express';
import { routes } from './routes.js';
import { failure } from "./util.js";
import redis from './middleware/redis.js';
import mysql from './middleware/mysql.js';


mysql.initialize().then(() => {
  const app: Express = express();
  // body-paser
  app.use(express.json());
  app.use(redis);
  app.use(function redis(req, res, next) {
    req.db = mysql.manager;
    next();
  })
  // auto load controller
  routes.forEach(route => {
    app[route.method](route.route, (req: Request, res: Response, next: Function) => {
      const result = route.controller(req, res, next)
      if (result instanceof Promise) {
        result
        .then(result => result !== null && result !== undefined ? res.send(result) : undefined)
        .catch(err => {
          console.log('http error', err);
          res.send(failure(err));
        });
      } else if (result !== null && result !== undefined) {
        res.json(result);
      }
    });
  });
  const port = process.env.PORT;
  app.listen(port, () => {
    console.log(`⚡️[server]: Server is running at http://localhost:${port}`);
  });
});
