import { Module } from '@nestjs/common';
import { SequelizeModule } from '@nestjs/sequelize';
import { Surl } from './surl.model';
import { SurlsService } from './surls.service';

@Module({
  imports: [SequelizeModule.forFeature([Surl])],
  providers: [SurlsService],
  exports: [SurlsService]
})
export class SurlsModule {}