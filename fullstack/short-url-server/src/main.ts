import { NestFactory } from '@nestjs/core';
import 'reflect-metadata';
import { createConnection } from 'typeorm';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  await createConnection();
  app.enableCors();
  await app.listen(3000);
}
bootstrap();
