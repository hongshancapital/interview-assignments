import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { UrlMapping } from './entity/url_mapping.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([UrlMapping]),
    TypeOrmModule.forRoot({
      type: 'postgres',
      host: '192.168.0.110',
      port: 5432,
      username: 'root',
      password: 'abc123',
      database: 'demo',
      entities: [UrlMapping],
    }),
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
