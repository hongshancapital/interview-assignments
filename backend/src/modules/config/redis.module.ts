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
          name: config.get('REDIS_NAME'),
          host: config.get('REDIS_HOST'),
          port: config.get('REDIS_PORT'),
          db: config.get('REDIS_DB'),
          password: config.get('REDIS_PWD'),
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
