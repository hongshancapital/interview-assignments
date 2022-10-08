import express = require("express");
import logger = require("morgan");
import * as bodyParser from "body-parser";

import  ShortUrlRoute  from "./routes/shorturlroute";
import { redisCache } from "./cache/redis"
import { DatabaseProvider } from "./db/databaseprovider";
import { unCoughtErrorHandler } from "./exception/errorcode"

/**
 * The server.
 *
 * @class Server
 */
export class Server {

  public app: express.Application;

  public static bootstrap(): Server {
    return new Server();
  }

  public static async InitServer() {
    await Promise.all([redisCache.initCache(), DatabaseProvider.InitDB()]);
  }

  public static async UnitServer() {
    await Promise.all([redisCache.UnitCache(), DatabaseProvider.UnitDB()]);
  }

  constructor() {
    //create expressjs application
    this.app = express();

    //configure application
    this.config();

    //add routes
    this.routes();
  }

  public config() {
    //mount logger
    this.app.use(logger("dev"));

    //mount json form parser
    this.app.use(bodyParser.json());

    //mount query string parser
    this.app.use(bodyParser.urlencoded({
      extended: true
    }));

    //error handling
    this.app.use(unCoughtErrorHandler);
  }

  private routes() {
    let router: express.Router;
    router = express.Router();

    //use router middleware
    this.app.use(ShortUrlRoute);
  }
}

