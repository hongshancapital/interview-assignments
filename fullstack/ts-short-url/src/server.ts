import * as bodyParser from "body-parser";
import cookieParser = require("cookie-parser");
import express = require("express");
import logger = require("morgan");
import * as path from "path";
import errorHandler = require("errorhandler");


import { IndexRoute } from "./routes/index";
import { ShortUrlRoute } from "./routes/shorturlroute";


/**
 * The server.
 *
 * @class Server
 */
export class Server {

  public app: express.Application;
  /**
   * Bootstrap the application.
   *
   * @class Server
   * @method bootstrap
   * @static
   * @return {ng.auto.IInjectorService} Returns the newly created injector for this app.
   */
  public static bootstrap(): Server {
    return new Server();
  }

  /**
   * Constructor.
   *
   * @class Server   
   * @constructor
   */
  constructor() {
    //create expressjs application
    this.app = express();

    //configure application
    this.config();

    //add routes
    this.routes();

    this.tsTest();
    //add api
  //  this.api();
  }

  /**
   * Create REST API routes
   *
   * @class Server
   * @method api
   */
  public api() {
    //empty for now

    let router: express.Router;
    router = express.Router();

    ShortUrlRoute.create(router);

    //use router middleware
    this.app.use(router);

    this.tsTest();
  }

  /**
   * Configure application
   *
   * @class Server
   * @method config
   */
  public config() {
    //add static paths
    this.app.use(express.static(path.join(__dirname, "public")));

    //configure pug
    this.app.set("views", path.join(__dirname, "views"));
    this.app.set("view engine", "pug");

    //mount logger
    this.app.use(logger("dev"));

    //mount json form parser
    this.app.use(bodyParser.json());

    //mount query string parser
    this.app.use(bodyParser.urlencoded({
      extended: true
    }));

    //mount cookie parser middleware
    this.app.use(cookieParser("SECRET_GOES_HERE"));

    // catch 404 and forward to error handler
    this.app.use(function(err: any, req: express.Request, res: express.Response, next: express.NextFunction) {
        err.status = 404;
        next(err);
    });

    //error handling
    this.app.use(errorHandler());
  }

  /**
   * Create and return Router.
   *
   * @class Server
   * @method routes
   * @return void
   */
  private routes() {
    let router: express.Router;
    router = express.Router();

    //IndexRoute
    IndexRoute.create(router);

    //ShortUrlRoute
    ShortUrlRoute.create(router);

    //use router middleware
    this.app.use(router);
  }

  public static value2: string;

  private tsTest() {

    let f1 = async ():Promise<string> =>{ return "hello1";}
    let f2 = async (value2: string) =>{ value2 = await f1();console.log(value2);};
    
//    f1().then(value=>{Server.value2 = value});
    f2(Server.value2);
  }

}
