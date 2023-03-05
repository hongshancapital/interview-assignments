import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UrlModule } from './url/url.module';
import { join } from 'path';

@Module({
  imports: [
    ConfigModule.forRoot(),
    TypeOrmModule.forRootAsync({
      imports: [ConfigModule],
      useFactory: (config: ConfigService) => ({
        type: 'mysql',
        host: config.getOrThrow('DB_HOST'),
        port: +config.getOrThrow('DB_PORT'),
        username: config.getOrThrow('DB_USERNAME'),
        password: config.getOrThrow<string>('DB_PASSWORD'),
        database: config.getOrThrow<string>('DB_DATABASE'),
        entities: [join(__dirname, '**', '*.entity.{ts,js}')],
        synchronize: !!config.get('DB_SYNC'),
        logging: !!config.get('DB_LOG'),
        timezone: '+08:00',
      }),
      inject: [ConfigService],
    }),

    UrlModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule { }
