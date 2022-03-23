import { MiddlewareConsumer, Module, NestModule } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';

import { ConfigModule } from './modules/config/config.module';
import { ShorturlModule } from './modules/shorturl/shorturl.module';
import { AppModule } from './modules/app/app.module';
import { RedisConfigModule } from './modules/config/redis.module';
import { DatabaseConfigModule } from './modules/config/database.module';

@Module({
  imports: [
    RedisConfigModule,
    DatabaseConfigModule,
    ShorturlModule,
    AppModule
  ],
  controllers: [],
  providers: [],
})
export class MainModule implements NestModule {
  constructor() { }

  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply()
      .forRoutes('api/*');
  }
}
