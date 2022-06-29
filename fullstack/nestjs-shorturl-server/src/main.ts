import { ValidationPipe } from '@nestjs/common'
import { NestFactory } from '@nestjs/core'
import type { NestExpressApplication } from '@nestjs/platform-express'
import chalk from 'chalk'
import compression from 'compression'
import { AppModule } from './app.module'
import { PrismaService } from './modules/shared/prisma.service'
import { setupSwagger } from './swagger/setup'

async function bootstrap() {
  const app = await NestFactory.create<NestExpressApplication>(AppModule, {
    cors: true
  })

  // 接口统一前缀
  app.setGlobalPrefix('api')

  // 请求校验
  app.useGlobalPipes(
    new ValidationPipe({
      // 白名单，剔除掉未在 dto 中定义的传参
      whitelist: true
    })
  )
  // TODO: 使用 app.useGlobalFilters 和 filter 进行自定义异常捕获与处理

  // const prisma = await app.get<PrismaService>(PrismaService)
  const prismaService = app.get<PrismaService>(PrismaService)
  prismaService.enableShutdownHooks(app)

  app.use(compression())

  // 生成 Swagger json 及接口文档，open http://localhost:3000/endpoints.html
  if (process.env.SWAGGER_ENABLE !== 'false') {
    setupSwagger(app)
  }
  // const configService = app.get<ConfigService>(ConfigService)
  // process.env.PORT 等效于 configService.get('PORT')
  await app.listen(process.env.PORT || 3000)

  // chalk@4.1.2 以上版本是 ES Module，只能通过这种方式引入
  // const chalk = await import('chalk')

  console.log(
    chalk.green(
      `⚡️ Application is running on: ${chalk.underline(
        (await app.getUrl()).replace('[::1]', 'localhost')
      )}`
    )
  )
}

bootstrap()
