import mongoose, { Connection, Mongoose } from "mongoose";
import { container, singleton } from 'tsyringe';

import config from "../config";
import { logger } from "../utils/logger";


@singleton()
export class MongoService{
  public mongoConnection!: Connection;
  public mongoClient!: Mongoose;
  public ready = false;

  constructor() {
    this.init();
  }

  init() {
    try {
      this.mongoClient = mongoose;
      this.mongoConnection = this.mongoClient.connection;
      this.mongoConnection.on('error', (error: any)=> {
        logger.error(`MongoDB error: ${error}`);
        this.ready = false;
      });
      this.mongoConnection.on('connected', ()=> {
        logger.info(`=================================`);
        logger.info(`🚀 MongoDB connect succeeded!`);
        logger.info(`=================================`);
        this.ready = true;
      });
      this.mongoConnection.on('disconnected', ()=> {
        logger.warn(`MongoDB disconnect!`);
        this.ready = false;
      });
      this.mongoConnection.on('close', ()=> {
        logger.warn(`MongoDB close!`);
        this.ready = false;
      });
      return this.mongoClient;
    } catch (error) {
      logger.error(`MongoService init error: ${error}`);
      this.ready = false;
    }
  }

  async connect() {
    if (this.ready) {
      return this.ready;
    }
    try {
      this.mongoClient = await this.mongoClient.connect(config.get('mongo.uri'), config.get('mongo.options'));
      this.ready = true;
    } catch(error) {
      logger.error(`MongoDB connect error: ${error}`);
      this.ready = false;
    }
    return this.ready;
  }

  async disconnect() {
    if (!this.ready) {
      return !this.ready;
    }
    try {
      await this.mongoClient.disconnect();
      this.ready = false;
    } catch(error) {
      logger.error(`MongoDB disconnect error: ${error}`);
    }
    return !this.ready;
  }

}

const mongoService = container.resolve(MongoService);
export default mongoService;