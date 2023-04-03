import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Record } from './record.entity';

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'better-sqlite3',
      database: 'demo.db',
      dropSchema: true,
      entities: [Record],
      synchronize: true,
    }),
    TypeOrmModule.forFeature([Record]),
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
