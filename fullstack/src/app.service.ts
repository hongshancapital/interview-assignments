import { Injectable } from '@nestjs/common';
import { Sequelize } from 'sequelize-typescript';
import { SurlsService } from './surls/surls.service';

@Injectable()
export class AppService {
  constructor(
    private sequelize: Sequelize,
    private surlsService: SurlsService
    ) {}

  getHello(): string {
    return 'Hello World!';
  }

  async getLongUrl(surl: string): Promise<string> {
    const shortUrl = await this.surlsService.getLongUrl(surl);
    return shortUrl;
  }

  async LongToShort(longUrl: string): Promise<string> {
    const shortUrl = await this.surlsService.LongToShort(longUrl);
    return `Long url: ${shortUrl}`;
  }
}

