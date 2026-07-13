import path from 'path'
import { Module } from '@nestjs/common'
import { ConfigModule, ConfigService } from '@nestjs/config'
import { ServeStaticModule } from '@nestjs/serve-static'
import { AppController } from './app.controller'
import { AppService } from './app.service'
import { SharedModule } from './modules/shared/shared.module'
import { UrlModule } from './modules/short-url/short-url.module'

@Module({
  imports: [
    // 读取 .env 注入到环境变量中，即 process.env.{VARIABLE}
    ConfigModule.forRoot({
      isGlobal: true
      // envFilePath: '.env'
    }),
    // 静态文件服务，主要用于 static/endpoints.html，即接口文档
    ServeStaticModule.forRootAsync({
      inject: [ConfigService],
      useFactory: async (configService: ConfigService) => {
        return [
          {
            // 静态文件根目录
            rootPath: path.resolve(
              process.cwd(),
              configService.get('RESOURCE_STATIC_PATH') || 'static'
            ),
            exclude: ['/api/*', '/swagger*'],
            serveStaticOptions: {
              index: 'endpoints.html'
            }
          }
        ]
      }
    }),
    // 全局共享模块，使用 @Global() 装饰
    SharedModule,
    // 将业务模块注册进根模块中
    UrlModule
  ],
  controllers: [AppController],
  providers: [AppService],
  exports: [
    /* AppService, */
  ]
})
export class AppModule {}
