import { Module } from '@nestjs/common';
import { IdService } from './id.service';

@Module({
  providers: [IdService],
  exports: [IdService],
})
export class IdModule {}
