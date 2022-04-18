import { Module } from '@nestjs/common';
import { SequelizeModule } from '@nestjs/sequelize';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { Surl } from './surls/surl.model';
import { SurlsModule } from './surls/surls.module';

@Module({
  imports: [
    SequelizeModule.forRoot({
    }),
    SurlsModule
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
