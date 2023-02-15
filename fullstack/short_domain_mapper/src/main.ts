import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { SwaggerModule, DocumentBuilder } from '@nestjs/swagger';

export async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  SwaggerModule.setup(
    '/swagger',
    app,
    SwaggerModule.createDocument(
      app,
      new DocumentBuilder()
        .setTitle('短域名系统')
        .setDescription(`短域名系统接口文档`)
        .setVersion('1.0.0')
        .addBearerAuth()
        .build(),
    ),
  );

  await app.listen(3000, () => {
    console.log('App started at http://localhost:3000');
    console.log('Swagger Doc at http://localhost:3000/swagger');
  });

  return app;
}
export default bootstrap();
