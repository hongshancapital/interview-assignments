import { Module } from '@nestjs/common';
import { IdfactoryService } from './idfactory.service';

@Module({
  providers: [IdfactoryService],
  exports: [IdfactoryService]
})
export class IdfactoryModule { }
