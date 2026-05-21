/**
 * api test
 * https://github.com/nestjs/nest/blob/master/sample/05-sql-typeorm/test/users/users.e2e-spec.ts
 */
import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication, ValidationPipe } from '@nestjs/common';
import * as request from 'supertest';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ShortModule } from './short.module';
import { CreateShortDto } from './dto/create-short.dto';
import { HttpExceptionFilter } from '../filters/http-exception.filter';
import { TransformInterceptor } from '../interceptor/transform';
import * as dotenv from 'dotenv'
dotenv.config()

describe('Users - /users (e2e)', () => {
  let app: INestApplication;

  beforeAll(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [
        TypeOrmModule.forRoot({
          type: 'mysql',
          host: process.env.DB_HOST,
          port: parseInt(process.env.DB_PORT),
          password: process.env.DB_PASSWORD,
          username: process.env.DB_USERNAME,
          database: process.env.DB_DATABASE,
          synchronize: false,
          autoLoadEntities: true,
        }),
        ShortModule,
      ],
    }).compile();

    app = moduleFixture.createNestApplication();
    app.useGlobalPipes(new ValidationPipe());
    app.useGlobalFilters(new HttpExceptionFilter())
    app.useGlobalInterceptors(new TransformInterceptor())
    await app.init();
  });

  describe("Users - /users (e2e)", () => {
    it("GET /short/all", async () => {
      return request(app.getHttpServer())
        .get("/short/all")
        .expect(200)
        .then((res) => {
          expect(res.body).toMatchObject({ msg: "请求成功" });
        });
    });

    it("POST /create (fail)", async () => {
      return request(app.getHttpServer())
        .post("/short/create")
        .send({
          url: "bab url",
        })
        .expect(400)
        .then((res) => {
          expect(res.body).toMatchObject({ msg: "url must be a URL address" });
        });
    });

    it("POST /create (success)", async () => {
      return request(app.getHttpServer())
        .post("/short/create")
        .send({ url: "https://docs.nestjs.com/" } as CreateShortDto)
        .expect(201)
        .then((res) => {
          expect(res.body).toMatchObject({ msg: "请求成功" });
        });
    });

    it("GET /search (fail)", async () => {
      return request(app.getHttpServer())
        .get("/short/search/6sRYvlWYssss")
        .expect(400)
        .then((res) => {
          expect(res.body).toMatchObject({ msg: "shortKey must be shorter than or equal to 8 characters" });
        });
    });

    it("GET /search (fail)", async () => {
      return request(app.getHttpServer())
        .get("/short/search/a")
        .expect(400)
        .then((res) => {
          expect(res.body).toMatchObject({ msg: "shortKey must be longer than or equal to 4 characters" });
        });
    });

    it("GET /search (success)", async () => {
      return request(app.getHttpServer())
        .get("/short/search/6sRYvlWY")
        .expect(200)
        .then((res) => {
          expect(res.body).toMatchObject({ msg: "请求成功", data: {}});
        });
    });
  })
  
  afterAll(async () => {
    await app.close();
  });
});