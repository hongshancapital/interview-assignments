import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { IdfactoryModule } from './idfactory/idfactory.module';
import { KvstoreModule } from './kvstore/kvstore.module';

@Module({
  imports: [IdfactoryModule, KvstoreModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule { }
