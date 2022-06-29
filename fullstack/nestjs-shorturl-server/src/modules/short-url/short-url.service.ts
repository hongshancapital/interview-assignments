import { Injectable } from '@nestjs/common'
// eslint-disable-next-line @typescript-eslint/consistent-type-imports
import { PrismaService } from '../shared/prisma.service'

@Injectable()
export class UrlService {
  constructor(private readonly prisma: PrismaService) {}

  async findByUrl(url: string) {
    const item = await this.prisma.url.findFirst({
      where: {
        url
      }
    })
    return item
  }

  async create(url: string, shortUrl: string) {
    const item = await this.prisma.url.create({
      data: {
        url,
        shortUrl
      }
    })
    return item
  }

  async findByShortUrl(shortUrl: string) {
    const item = await this.prisma.url.findFirst({
      where: {
        shortUrl
      }
    })
    return item
  }
}
