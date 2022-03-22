import { MiddlewareConsumer, Module, NestModule } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { RedisModule } from '@nestjs-modules/ioredis';

import { ConfigModule } from './modules/config/config.module';

import { ShorturlModule } from './modules/shorturl/shorturl.module';

import * as ormconfig from '../ormconfig';
import { AppModule } from './modules/app/app.module';

@Module({
  imports: [
    TypeOrmModule.forRoot({}),
    
    RedisModule.forRootAsync({
      useFactory: () => ({
        config: { 
          name: ormconfig.database,
          host: ormconfig.host,
          port: 6379,
          password: ormconfig.password,
        },
      }),
    }),
    ConfigModule,
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
