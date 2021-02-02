import { injectable, inject } from 'inversify';
import 'reflect-metadata';
import * as express from 'express';
import * as morgan from 'morgan';
import Controller from '../controller/controller';
import Validator from '../controller/validator';
import { LoggerStream } from '../logger';
import { SERVICE_ENDPOINT } from '../config';

@injectable()
export default class App {
  public app: express.Application;

  private controller: Controller;

  constructor(@inject(Controller) controller:Controller) {
    this.app = express();
    this.controller = controller;

    this.app.use(express.json());
    this.app.use(morgan('dev', { stream: new LoggerStream() }));

    const router = express.Router();
    router.route('/').post(
      (req, res, next) => Validator.on_create_request(req, res, next),
      (req, res) => this.controller.create_short_url(req, res),
    );
    router.route('/:short_url').get(
      (req, res, next) => Validator.on_resolve_request(req, res, next),
      (req, res) => this.controller.resolve_short_url(req, res),
    );
    this.app.use(SERVICE_ENDPOINT, router);
  }
}
