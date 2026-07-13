import type { INestApplication } from '@nestjs/common'
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger'
import pkg from '../../package.json'

export function setupSwagger(app: INestApplication) {
  const options = new DocumentBuilder()
    .setTitle(pkg.description || `短链接服务接口文档`)
    // .setDescription(``)
    .setVersion(pkg.version || '0.0.1')
    .addTag('基础接口')
    // .addBearerAuth()
    .build()

  const document = SwaggerModule.createDocument(app, options, {})
  // open http://localhost:3000/swagger or http://localhost:3000/swagger-json
  // 前者由 swagger-ui-express 产生，后者来自于 swagger json 和 redoc
  SwaggerModule.setup('swagger', app, document, {
    swaggerOptions: {
      // persistAuthorization: true
    },
    customSiteTitle: '短链接服务接口文档'
  })
}
