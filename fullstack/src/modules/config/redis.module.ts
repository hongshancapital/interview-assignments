import { DynamicModule, Module } from '@nestjs/common';
import { RedisModule } from '@nestjs-modules/ioredis';
import { ConfigService } from './config.service';
import { ConfigModule } from './config.module';

const RedisOrm = (): DynamicModule => {
  const config = new ConfigService();

  return RedisModule.forRootAsync({
    useFactory: () => {
      return {
        config: { 
          name: config.get('DATABASE_NAME'),
          host: config.get('DATABASE_HOST'),
          port: config.get('REDIS_PORT'),
          password: config.get('DATABASE_PWD'),
        },
      }
    }
  })
}

@Module({
  imports: [
    ConfigModule,
    RedisOrm()
  ],
})
export class RedisConfigModule {}
