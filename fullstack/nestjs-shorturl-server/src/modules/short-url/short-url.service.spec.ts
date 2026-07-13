/* eslint-disable jest/no-commented-out-tests */
import { faker } from '@faker-js/faker'
import { Test } from '@nestjs/testing'
import type { Url as UrlModel } from '@prisma/client'
import { nanoid } from 'nanoid'
import { PrismaService } from '../shared/prisma.service'
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

const db = {
  url: {
    findFirst: jest.fn().mockResolvedValue(mockUrl),
    create: jest.fn().mockResolvedValue(mockUrl)
  }
}

describe('UrlService', () => {
  let urlService: UrlService
  let prisma: PrismaService

  beforeEach(async () => {
    const moduleRef = await Test.createTestingModule({
      providers: [
        UrlService,
        {
          provide: PrismaService,
          useValue: db
        }
      ]
    }).compile()

    urlService = moduleRef.get<UrlService>(UrlService)
    prisma = moduleRef.get<PrismaService>(PrismaService)
  })

  it('should be define', () => {
    expect(urlService).toBeDefined()
  })

  describe('findByUrl', () => {
    it('should return a "Url" object', async () => {
      const result = await urlService.findByUrl(mockUrl.url)
      expect(result).toEqual(mockUrl)
    })
    // it('should return null', async () => {
    //   jest.spyOn(prisma.url, 'findFirst').mockResolvedValueOnce(null)
    //   const result = await urlService.findByUrl('http://notexist.com')
    //   expect(result).toEqual(null)
    // })
  })

  describe('create', () => {
    it('should return a "Url" object', async () => {
      const result = await urlService.create(mockUrl.url, mockUrl.shortUrl)
      expect(result).toEqual(mockUrl)
    })
  })

  describe('findByShortUrl', () => {
    it('should return a "Url" object', async () => {
      const result = await urlService.findByShortUrl(mockUrl.shortUrl)
      expect(result).toEqual(mockUrl)
    })
    // it('should return null', async () => {
    //   const result = await urlService.findByShortUrl('https://notexist.com')
    //   expect(result).toEqual(null)
    // })
  })
})
