import { NestFactory } from '@nestjs/core';
import {
    NestExpressApplication,
} from '@nestjs/platform-express';

import { AppModule } from './app.module';

async function bootstrap() {
    const app = await NestFactory.create<NestExpressApplication>(AppModule);
    app.disable('x-powered-by');
    const server = await app.listen(5000);
}

bootstrap();