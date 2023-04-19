import { NestFactory } from '@nestjs/core';
import { SwaggerModule, DocumentBuilder } from '@nestjs/swagger';
import { NestExpressApplication } from '@nestjs/platform-express';

import rateLimit from 'express-rate-limit';
import * as cookieParser from 'cookie-parser';
import helmet from 'helmet';
import * as bodyParser from 'body-parser';

import { MainModule } from './main.module';

import { ConfigService } from './modules/config/config.service';
import { ValidationPipe } from '@nestjs/common';

async function bootstrap() {
  const app = await NestFactory.create<NestExpressApplication>(MainModule, {});
  const config = new ConfigService();
  app.enableCors({
    credentials: true,
    origin: config.getCrossOrigin()
  });
  app.use(bodyParser.urlencoded({extended: true}));
  app.use(bodyParser.json());
  // 统一 IP 访问限制，防止收到攻击
  app.use(
    rateLimit({
      windowMs: 5 * 60 * 1000, // 15 minutes
      max: 1000, // 100 limit each IP to 100 requests per windowMs
    }),
  );
  // Helmet 可以帮助保护您的应用免受一些众所周知的 Web 漏洞的影响
  app.use(helmet());
  // 跨站点请求伪造（称为 CSRF 或 XSRF）是一种恶意利用网站，其中未经授权的命令从 Web 应用程序信任的用户传输。
  app.use(cookieParser());

  app.useGlobalPipes(new ValidationPipe());

  const options = new DocumentBuilder()
    .setTitle('Short URL')
    .setVersion('0.1')
    .build();

  const document = SwaggerModule.createDocument(app, options, {});
  SwaggerModule.setup('api/swagger', app, document);


  await app.listen(3332, '0.0.0.0', async () => {
    console.log(`Short URL RESTful API Doc Server started on: ${await app.getUrl()}/api/swagger`);
  });
}
bootstrap();
