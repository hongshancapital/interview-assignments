import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { getRedisConfig, getDbConfig } from './config';

async function bootstrap() {
  console.log(getDbConfig());
  console.log(getRedisConfig());
  const app = await NestFactory.create(AppModule);
  app.enableShutdownHooks();
  await app.listen(3000);
}
bootstrap();
