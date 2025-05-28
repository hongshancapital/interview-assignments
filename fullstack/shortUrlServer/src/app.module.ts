import { Module, MiddlewareConsumer } from '@nestjs/common';

// 引入数据库的及配置文件
import { TypeOrmModule } from '@nestjs/typeorm';
import { ShortUrlMapModule } from './modules/shorturlmap/shorturlmap.module';
import { filterMiddleware } from './middlewares/filter'
import { BloomFilterService } from './services/bloomFilter.service';
import { RedisService } from './services/redis.service'
import { APP_FILTER } from '@nestjs/core';
import { AllExceptionsFilter } from './exception';
import { ConfigModule } from '@nestjs/config'


@Module({
    imports: [
        ConfigModule.forRoot({
            isGlobal: true,
            envFilePath: '.env.local'
        }),
        TypeOrmModule.forRoot({
            type: 'mysql',
            host: process.env.PRIMARY_MYSQL_HOST,
            port: parseInt(process.env.PRIMARY_MYSQL_PORT),
            username: process.env.PRIMARY_MYSQL_USERNAME,
            password: process.env.PRIMARY_MYSQL_PASSWORD,
            database: process.env.PRIMARY_MYSQL_DATABASE,
            entities: [__dirname + '/**/*.entity{.ts,.js}'],
            synchronize: true,
            name: 'primary', // 0 库
        }),
        TypeOrmModule.forRoot({
            type: 'mysql',
            host: process.env.BACKUP_MYSQL_HOST,
            port: parseInt(process.env.BACKUP_MYSQL_PORT),
            username: process.env.BACKUP_MYSQL_USERNAME,
            password: process.env.BACKUP_MYSQL_PASSWORD,
            database: process.env.BACKUP_MYSQL_DATABASE,
            entities: [__dirname + '/**/*.entity{.ts,.js}'],
            synchronize: true,
            name: 'backup', // 1 库
        }),
        ShortUrlMapModule
    ],
    providers: [
        BloomFilterService,
        RedisService,
        {
            provide: APP_FILTER,
            useClass: AllExceptionsFilter
        }
        // ShortUrlMapService
    ]
})
export class AppModule {
    configure(consumer: MiddlewareConsumer) {
        consumer.apply(filterMiddleware).forRoutes('*')
    }
    // constructor(private readonly connection: Connection) { }
}
