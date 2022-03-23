import { Module } from '@nestjs/common';
import { ShorturlModule } from '../shorturl/shorturl.module';
import { AppController } from './app.controller';
@Module({
  imports: [
    ShorturlModule
  ],
  controllers: [
    AppController,
  ],
  providers: [],
  exports: [],
})
export class AppModule {}
