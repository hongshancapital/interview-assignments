/** @format */

import express from 'express';
import { Service } from './service';
import { Db } from './db';
import * as core from 'express-serve-static-core';

export class App {
  public readonly service: Service;

  public express: core.Express;

  constructor() {
    this.service = new Service();
    this.express = express();
  }

  /**
   * 初始化app
   */
  public async init() {
    await Db.connectDB();
    this.express.get('/getShortDomainName', this.service.getShortDomainName.bind(this.service));
    this.express.get('/getOriginUrl', this.service.getOriginUrl.bind(this.service));
  }
}
