import { NestFactory } from '@nestjs/core';
import { SwaggerModule, DocumentBuilder } from '@nestjs/swagger';
import { NestExpressApplication } from '@nestjs/platform-express';

import rateLimit from 'express-rate-limit';
import * as cookieParser from 'cookie-parser';
import helmet from 'helmet';
import * as bodyParser from 'body-parser';

import { MainModule } from './main.module';
import { HttpExceptionFilter } from './common/any-exception.filter';
import { TransformInterceptor } from './common/transform.interceptor';

import { ConfigService } from './modules/config/config.service';

async function bootstrap() {
  const app = await NestFactory.create<NestExpressApplication>(MainModule, {});
  const config = new ConfigService();
  app.enableCors({
    credentials: true,
    origin: config.getCrossOrigin()
  });
  app.use(bodyParser.urlencoded({extended: true}));
  app.use(bodyParser.json());
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
  app.useGlobalFilters(new HttpExceptionFilter());
  app.useGlobalInterceptors(new TransformInterceptor());

  const options = new DocumentBuilder()
    .setTitle('Short URL')
    .setDescription('The API description !')
    .setContact('FuYin', '//url.co', 'fooying@qq.com')
    .setVersion('0.1')
    .addTag('Short URL')
    .addApiKey({
        type: 'apiKey',
        name: 'X-Api-Key',
        in: 'header'
      },
      'APIKey'
    )
    .addBearerAuth()
    .build();

  const document = SwaggerModule.createDocument(app, options, {});
  SwaggerModule.setup('api/swagger', app, document);


  await app.listen(3332, '0.0.0.0', async () => {
    console.log(`Short URL RESTful API Doc Server started on: ${await app.getUrl()}/api/swagger`);
  });
}
bootstrap();
