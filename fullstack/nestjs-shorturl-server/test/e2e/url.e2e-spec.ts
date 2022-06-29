/* eslint-disable jest/expect-expect */
import { faker } from '@faker-js/faker'
import type { INestApplication } from '@nestjs/common'
import { ValidationPipe } from '@nestjs/common'
import type { TestingModule } from '@nestjs/testing'
import { Test } from '@nestjs/testing'
import type { Url as UrlModel } from '@prisma/client'
import { nanoid } from 'nanoid'
import request from 'supertest'
import { AppModule } from '../../src/app.module'
import { PrismaService } from '../../src/modules/shared/prisma.service'
import { UrlModule } from '../../src/modules/short-url/short-url.module'

describe('Url (e2e)', () => {
  let app: INestApplication
  let prisma: PrismaService
  let existUrlRecord: UrlModel | null
  let newUrlRecord: UrlModel

  const urlShape = expect.objectContaining({
    id: expect.any(String),
    url: expect.any(String),
    shortUrl: expect.any(String),
    createdAt: expect.any(String)
  })
  const badRequestShape = expect.objectContaining({
    statusCode: 400,
    message: expect.arrayContaining([expect.any(String)]),
    error: expect.any(String)
  })
  const notFoundShape = expect.objectContaining({
    statusCode: 404,
    message: expect.any(String),
    error: expect.any(String)
  })

  beforeEach(async () => {
    const moduleRef: TestingModule = await Test.createTestingModule({
      imports: [AppModule, UrlModule]
    }).compile()

    app = moduleRef.createNestApplication()
    prisma = app.get<PrismaService>(PrismaService)

    app.setGlobalPrefix('api')
    app.useGlobalPipes(new ValidationPipe({ whitelist: true }))

    await app.init()

    const ul = faker.internet.url()
    // 新创建一个记录
    newUrlRecord = await prisma.url.create({
      data: {
        url: ul,
        shortUrl: `https://hs.cn/${nanoid(5).toLowerCase()}`
      }
    })
    // 查询已存在的记录
    existUrlRecord = await prisma.url.findFirst({
      where: {
        url: ul
      }
    })
  })

  describe('GET /api/url', () => {
    it('returns a exist url record', async () => {
      const { status, body } = await request(app.getHttpServer()).get(
        `/api/url?url=${existUrlRecord?.url}`
      )
      expect(status).toBe(200)
      expect(body).toStrictEqual(urlShape)
    })

    it('returns a new url record', async () => {
      const { status, body } = await request(app.getHttpServer()).get(
        `/api/url?url=${faker.internet.url()}`
      )
      expect(status).toBe(200)
      expect(body).toStrictEqual(urlShape)
    })

    it('returns HTTP status 400 when parameter is missing', async () => {
      const { status, body } = await request(app.getHttpServer()).get(
        `/api/url`
      )
      expect(status).toBe(400)
      expect(body).toStrictEqual(badRequestShape)
    })

    it('returns HTTP status 400 when parameter is error format', async () => {
      const { status, body } = await request(app.getHttpServer()).get(
        `/api/url?url=noturl`
      )
      expect(status).toBe(400)
      expect(body).toStrictEqual(badRequestShape)
    })
  })

  describe('GET /api/shorturl', () => {
    it('returns a exist url record', async () => {
      const { status, body } = await request(app.getHttpServer()).get(
        `/api/shorturl?shortUrl=${existUrlRecord?.shortUrl}`
      )
      expect(status).toBe(200)
      expect(body).toStrictEqual(urlShape)
    })

    it('returns HTTP status 400 when parameter is missing', async () => {
      const { status, body } = await request(app.getHttpServer()).get(
        `/api/shorturl`
      )
      expect(status).toBe(400)
      expect(body).toStrictEqual(badRequestShape)
    })

    it('returns HTTP status 400 when parameter is error format', async () => {
      const { status, body } = await request(app.getHttpServer()).get(
        `/api/shorturl?shortUrl=notshorturl`
      )
      expect(status).toBe(400)
      expect(body).toStrictEqual(badRequestShape)
    })

    it('returns HTTP status 404 when parameter is not exist', async () => {
      const { status, body } = await request(app.getHttpServer()).get(
        `/api/shorturl?shortUrl=http://notexist.com`
      )
      expect(status).toBe(404)
      expect(body).toStrictEqual(notFoundShape)
    })
  })

  afterAll(async () => {
    // TODO: 结束后清空测试产生的数据
    await app.close()
  })
})
