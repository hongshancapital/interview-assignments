import { DynamicModule, Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { DatabaseType, LoggerOptions } from 'typeorm';
import * as path from 'path';
import { ConfigService } from './config.service';

export interface IDbConfig {
  name?: string;
  type?: DatabaseType;
  database?: string;
  host?: string;
  port?: string;
  username?: string;
  password?: string;
  logging?: LoggerOptions;
  entities?: string[];
  subscribers?: string[];
}


@Module({})
export class DatabaseConfigModule {
  static forRoot(options: IDbConfig): DynamicModule {
    const config = new ConfigService();
    const configs = {
      type: config.get('DATABASE_TYPE') as any,
      name: config.get('DATABASE_NAME'),
      database: config.get('DATABASE_DB'),
      host: config.get('DATABASE_HOST'),
      port: Number(config.get('DATABASE_PORT')),
      username: config.get('DATABASE_USER'),
      password: config.get('DATABASE_PWD'),
      logging: [ 'error', 'query', 'schema' ] as any,
      entities: [
        path.resolve(
          __dirname,
          `../../modules/**/*.entity{.ts,.js}`,
        ),
      ],
      subscribers: [
        path.resolve(
          __dirname,
          `../../migrations/*{.ts,.js}`,
        ),
      ],
      ...options,
      // ！！！！开启小心数据被删除
      // synchronize: Boolean(config.get('DATABASE_SYNCHRONIZE')),
      // dropSchema: Boolean(config.get('DATABASE_DROPSCHEMA')),
    }
    console.log(configs);
    return {
      module: DatabaseConfigModule,
      imports: [
        TypeOrmModule.forRoot(configs),
      ],
    };
  }
}
