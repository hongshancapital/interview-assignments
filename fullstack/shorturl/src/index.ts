import express, {Express} from 'express';

import cors from 'cors';

import Database from './infra/Database';

import Configuration from './infra/Configuration';

import router from './routes';

async function bootstrap() {
  Configuration.init({
    envFilePath: [
      `.env/.env.${process.env.NODE_ENV || 'development'}.local`,
      `.env/.env.${process.env.NODE_ENV || 'development'}`,
      `.env/.env.local`,
      `.env/.env`,
    ],
    expandVariables: true,
  });

  await Database.init({
    host: Configuration.getOrThrow<string>('MYSQL_HOST'),
    port: Configuration.getOrThrow<number>('MYSQL_PORT'),
    user: Configuration.getOrThrow<string>('MYSQL_USER'),
    password: Configuration.getOrThrow<string>('MYSQL_PASSWORD'),
    database: Configuration.getOrThrow<string>('MYSQL_DATABASE'),
  });

  const tableName = `${Configuration.getOrThrow<string>('MYSQL_PREFIX')}url`;

  // await Database.exec(`DROP TABLE IF EXISTS ${tableName};`);

  await Database.exec(`
    CREATE TABLE IF NOT EXISTS ${tableName} (
      id INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID', 
      url TEXT NULL COMMENT '链接', 
      alias VARCHAR(50) NULL COMMENT '别名', 
      domain VARCHAR(100) NULL COMMENT '域名',
      KEY alias (alias)
    ) COMMENT 'URL' ENGINE=InnoDB CHARSET=utf8mb4 AUTO_INCREMENT=0;
  `);

  const app: Express = express();

  app.options("*", cors({
    origin: '*',
    methods: "GET,HEAD,PUT,PATCH,POST,DELETE",
    preflightContinue: false,
    allowedHeaders: '*',
  }));

  app.use(express.json());

  app.use(express.urlencoded());

  app.use(router);

  return app;
}

const port = parseInt(process.env.CLUSTER_PORT) || 8000;

bootstrap()
  .then(app => app.listen(port, () => console.log(`Listen on %s`, port)));
