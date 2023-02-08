import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm'
import { ShortService } from './short.service';
import { ShortController } from './short.controller';
import { Short } from './entities/short.entity'


@Module({
  imports: [TypeOrmModule.forFeature([Short])],
  controllers: [ShortController],
  providers: [ShortService],
})
export class ShortModule {}
