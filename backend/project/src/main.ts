import { NestFactory } from '@nestjs/core';
import { AppModule } from './modules/app.module';
import { config } from './common/config';
import { ValidationPipe } from '@nestjs/common';
import { Logger } from '@nestjs/common';
import { Response } from './interceptor/response';
import { ErrFilter } from './interceptor/err';
async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  // 添加参数校验
  app.useGlobalPipes(new ValidationPipe());
  app.useGlobalInterceptors(new Response());
  app.useGlobalFilters(new ErrFilter());
  await app.listen(config.port);
  let logger = new Logger('main');
  logger.log(`服务已启动，监听端口：${config.port}`);
}
bootstrap();
