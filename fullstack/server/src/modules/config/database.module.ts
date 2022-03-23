import { DynamicModule, Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import * as path from 'path';
import { ConfigModule } from './config.module';
import { ConfigService } from './config.service';

const Orm = (): DynamicModule => {
  const config = new ConfigService();
  const options = {
    name: config.get('DATABASE_NAME'),
    type: config.get('DATABASE_TYPE'),
    database: config.get('DATABASE_DB'),
    host: config.get('DATABASE_HOST'),
    port: config.get('DATABASE_PORT'),
    username: config.get('DATABASE_USER'),
    password: config.get('DATABASE_PWD'),
    entities: [
      path.resolve(
        __dirname,
        `../../modules/**/*.entity{.ts,.js}`,
      ),
    ],
    subscribers: [
      path.resolve(
        __dirname,
        `../../migrations/*.entity{.ts,.js}`,
      ),
    ],
    synchronize: Boolean(config.get('DATABASE_SYNCHRONIZE')),
    dropSchema: Boolean(config.get('DATABASE_DROPSCHEMA')),
  }
  return TypeOrmModule.forRoot(options);
};

@Module({
  imports: [
    ConfigModule,
    Orm()
  ],
})
export class DatabaseConfigModule {}
