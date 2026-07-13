/* eslint-disable jest/no-commented-out-tests */
import { faker } from '@faker-js/faker'
import { Test } from '@nestjs/testing'
import type { Url as UrlModel } from '@prisma/client'
import { nanoid } from 'nanoid'
import { UrlController } from './short-url.controller'
import { UrlService } from './short-url.service'

const shortUrl = `https://hs.cn/${nanoid(5).toLowerCase()}`
const mockUrl: Omit<UrlModel, 'createdAt'> & {
  createdAt: string
} = {
  id: 'cl4y9rcjk0002qcjdg92e58t7',
  url: faker.internet.url(),
  shortUrl,
  createdAt: new Date().toISOString()
}
// 校验错误：url 不合法
const badRequestForGetOrCreateShortUrl = {
  statusCode: 400,
  message: ['url must be an URL address'],
  error: 'Bad Request'
}
// 校验错误：shortUrl 不合法
const badRequestForGetUrl = {
  statusCode: 400,
  message: ['shortUrl must be an URL address'],
  error: 'Bad Request'
}
// 请求错误：数据不存在
const notFoundForGetUrl = {
  statusCode: 404,
  message: 'url 不存在',
  error: 'Bad Request'
}
// 有效 url 正则
const validUrlReg =
  /(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]+\.[^\s]{2,}|www\.[a-zA-Z0-9]+\.[^\s]{2,})/
// 有效短链正则
const validShortUrlReg = /https:\/\/hs\.cn\/[a-z\d]{5}/

describe('UrlController', () => {
  let urlController: UrlController
  let urlService: UrlService

  beforeEach(async () => {
    const moduleRef = await Test.createTestingModule({
      controllers: [UrlController],
      providers: [
        {
          provide: UrlService,
          useValue: {
            findByUrl: jest.fn().mockImplementation((url: string) => {
              // 链接存在，返回短链
              if (mockUrl.url === url) {
                return Promise.resolve(mockUrl)
              }
              // 返回校验错误
              return Promise.resolve(badRequestForGetOrCreateShortUrl)
            }),
            create: jest
              .fn()
              .mockImplementation((url: string, shortUrl: string) => {
                return Promise.resolve({
                  ...mockUrl,
                  url,
                  shortUrl: 'https://hs.cn/abcde'
                })
              }),
            findByShortUrl: jest.fn().mockImplementation((shortUrl: string) => {
              // shortUrl 格式不正确
              if (!validShortUrlReg.test(shortUrl)) {
                return Promise.resolve(badRequestForGetUrl)
              }
              // 模拟正确返回
              if (mockUrl.shortUrl === shortUrl) {
                return Promise.resolve(mockUrl)
              }
              // 返回数据不存在错误
              return Promise.resolve(notFoundForGetUrl)
            })
          }
        }
      ]
    }).compile()

    urlService = moduleRef.get<UrlService>(UrlService)
    urlController = moduleRef.get<UrlController>(UrlController)
  })

  it('should be define', () => {
    expect(urlController).toBeDefined()
  })

  describe('getOrCreateShortUrl', () => {
    // 已存在的链接
    it('should get a existing "Url" object', async () => {
      await expect(
        urlController.getOrCreateShortUrl({ url: mockUrl.url })
      ).resolves.toEqual(mockUrl)
    })
    // 一条新链接
    // it('should get a new "Url" object', async () => {
    //   const u = faker.internet.url()
    //   await expect(
    //     urlController.getOrCreateShortUrl({
    //       url: u
    //     })
    //   ).resolves.toEqual({
    //     ...mockUrl,
    //     url: u,
    //     shortUrl: 'https://hs.cn/abcde'
    //   })
    // })
    // 链接格式错误
    it('should get 400 status', async () => {
      await expect(
        urlController.getOrCreateShortUrl({ url: 'incorrect url' })
      ).resolves.toEqual(badRequestForGetOrCreateShortUrl)
    })
  })

  describe('getUrl', () => {
    // 返回存在的链接
    it('should get "Url" object', async () => {
      await expect(
        urlController.getUrl({ shortUrl: mockUrl.shortUrl })
      ).resolves.toEqual(mockUrl)
    })
    // 短链格式错误
    it('should get 400 status', async () => {
      await expect(
        urlController.getUrl({ shortUrl: 'incorrect url' })
      ).resolves.toEqual(badRequestForGetUrl)
    })
    // 短链不存在
    it('should get 404 status', async () => {
      await expect(
        urlController.getUrl({ shortUrl: 'https://hs.cn/nnnnn' })
      ).resolves.toEqual(notFoundForGetUrl)
    })
  })
})
