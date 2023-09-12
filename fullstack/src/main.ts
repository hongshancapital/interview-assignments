import { NestFactory } from '@nestjs/core';
import { Logger, ValidationPipe } from '@nestjs/common';

import { ShortUrlModule } from './short-url/short-url.module';
import { HttpExceptionFilter } from './shared/filters/http-exception/http-exception.filter';

async function bootstrap() {
  const app = await NestFactory.create(ShortUrlModule);
  app.useGlobalPipes(new ValidationPipe());
  app.useGlobalFilters(new HttpExceptionFilter(new Logger()));
  await app.listen(3000);
}
bootstrap();
