import { MiddlewareConsumer, Module, NestModule } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { config } from '../common/config';
import { ShortEntity } from '../entities/short.entity';
import { ShortModule } from './short/short.module';

@Module({
  imports: [
    ShortModule,
    // @ts-ignore
    TypeOrmModule.forRoot({
      ...config.database,
      entities: [ShortEntity],
      autoLoadEntities: true, // 自动加载实体类
    }),
  ],
})
export class AppModule {}
