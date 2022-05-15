import express, { Express } from 'express';
import dotenv from 'dotenv';
import { GlobalConfig } from './types/type'
import { MySQLStore } from './store/mysql'
import { RedisCache } from './cache/cache'
import { URLSvc } from './service/url'
import { URLController } from './controller/url'

class App {
  app: Express
  constructor() {
    this.app = express()
    this.start = this.start.bind(this)
  }

  private initConfig(): GlobalConfig {
    dotenv.config()
    const { 
      MYSQL_HOST,
      MYSQL_PORT,
      MYSQL_USER,
      MYSQL_PASSWORD,
      MYSQL_DATABASE,
      REDIS_HOST,
      REDIS_PORT,
      REDIS_USER,
      REDIS_PASSWORD,
    } = process.env
    
    return {
      mysql: {
        host: MYSQL_HOST as string,
        port: Number(MYSQL_PORT),
        database: MYSQL_DATABASE || 'url',
        user: MYSQL_USER || 'root',
        pass: MYSQL_PASSWORD || '',
      },
      redis: {
        host: REDIS_HOST,
        port: Number(REDIS_PORT),
        username: REDIS_USER,
        password: REDIS_PASSWORD,
      }
    }
  }

  start() {
    const { app } = this;
    const config = this.initConfig()
    const mysql = new MySQLStore(config.mysql)
    const redis = new RedisCache(config.redis)
    const svc = new URLSvc(mysql, redis)
    const controller = new URLController(svc)

    app.use(express.json());
  
    app.get('/api/v1/:shortURL', controller.getLongURL)
    app.post('/api/v1/short', controller.generateShortURL)
    return app
  }
}

export { App }
