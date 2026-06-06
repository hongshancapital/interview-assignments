import "reflect-metadata";
import { container, singleton } from 'tsyringe';

import http, { Server } from 'http';
import express, { Request, Response, NextFunction } from "express";
import config from "./config";
import { logger, TRACE_ID } from "./utils/logger";

import helmet from "helmet";
import cors from "cors";
import bodyParser from 'body-parser';
import compression from "compression";
import cookieParser from 'cookie-parser';
import { rate } from "./middlewares/limiter.middleware";
import { errorHandler } from "./middlewares/error.middleware";
import { asyncHook } from "./middlewares/asyncHook.middleware";

import { UrlRoutes } from "./routes/url.route";
import { MongoService } from "./services/mongo.service";
import { RedisService } from "./services/redis.service";

@singleton()
export class App {
  public app: express.Application;
  public port: number = config.get("port") || 3000;
  public httpServer!: Server;
  public ready = false;

  constructor(
    public readonly urlRoutes: UrlRoutes,
    public readonly mongoService: MongoService,
    public readonly redisService: RedisService
  ) {

    this.app = express();
    this.initializeDB().then();
    this.initializeMiddlewares();
    this.initializeRoutes();
    this.initializeErrorHandling();

    this.httpServer = http.createServer(this.app);
    this.httpServer.on('listening', () => {
      logger.info('http listening');
      this.ready = true;
    });
    this.httpServer.on('error', (error) => {
      logger.error('http server:', error.toString());
      this.ready = false;
    });
    this.httpServer.on('close', () => {
      logger.warn('http close');
      this.ready = false;
    });
  }

  private async initializeDB() {
    await this.mongoService.connect();
    await this.redisService.connect();
  }

  private initializeMiddlewares() {
    this.app.use(asyncHook);
    this.app.use(this.healthCheckMiddleware());
    this.app.use(this.logRequestsMiddleware());
    this.app.use(cors());
    this.app.use(helmet());
    this.app.use(compression());
    this.app.use(rate);
    this.app.use(bodyParser.urlencoded({ limit: '100mb', extended: true }));
    this.app.use(bodyParser.json());
    this.app.use(cookieParser());
  }

  logRequestsMiddleware() {
    return async (req: Request, res: Response, next: NextFunction) => {
      const startedAt = Date.now();
      const traceId = Reflect.get(req, TRACE_ID);
      const url = req.originalUrl;
      if (['GET', 'DELETE', 'HEAD', 'OPTIONS'].includes(req.method.toUpperCase())) {
        logger.info(`Incoming request: ${req.method.toUpperCase()} ${url} ${req.ip}, traceId: ${traceId}`, );
      } else {
        logger.info(`Incoming request: ${req.method.toUpperCase()} ${url} ${res.get('Content-Type') || 'unspecified-type'} ${res.get('content-length') || 'N/A'} ${req.ip}, traceId: ${traceId}`);
      }

      res.once('error', (err) => {
        const traceId = Reflect.get(req, TRACE_ID);
        logger.error(`Error in response: ${err.toString()}, err: ${err}, traceId: ${traceId}`);
      });

      res.once('close', () => {
        const traceId = Reflect.get(res, TRACE_ID);
        const duration = Date.now() - startedAt;
        logger.info(`Request completed: ${res.statusCode} ${req.method.toUpperCase()} ${url} ${res.get('Content-Type') || 'unspecified-type'} ${duration}ms, traceId: ${traceId}`);
      });

      return next();
    };
  }

  private healthCheckMiddleware() {
    return (req: Request, res: Response, next: NextFunction) => {
      if (req.path !== '/ping') {
        next();
      } else {
        this.ready = this.ready && this.mongoService.ready && this.redisService.ready;
        if (this.ready) {
          res.status(200).json('pong');
        } else {
          res.status(500).json('false');
        }
      }
    };
  }

  private initializeRoutes() {
    this.app.use('/', this.urlRoutes.getRouter());
  }

  private initializeErrorHandling() {
    this.app.use(errorHandler);
  }

  public listen() {
    this.httpServer.listen(this.port, async () => {
      logger.info(`=================================`);
      logger.info(`======= ENV: ${config.env} =======`);
      logger.info(`🚀 App listening on the port ${this.port}`);
      logger.info(`=================================`);
      this.ready = true;
    });
  }

  public getServer() {
    return this.httpServer;
  }
}

export const app = container.resolve(App);
export default app;
