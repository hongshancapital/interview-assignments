import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ShortUrlModule } from './short-url/short-url.module';
import { ShortUrlRepository } from './short-url/entity/short-url.repository';

const ENTITIES = [ShortUrlRepository];

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'mysql',
      host: '127.0.0.1',
      username: 'root',
      password: '#cycode_secret_ignore_everywhere',
      database: 'sequoia_demo',
      entities: [...ENTITIES],
      synchronize: true,
    }),
    ShortUrlModule
  ],
  controllers: [],
  providers: [],
})
export class AppModule {}
