import { Test } from '@nestjs/testing';
import { AppService } from './app.service';
import { AppModule } from './app.module';

describe('Test AppService', () => {
  let appService: AppService;
  let currCode: string;
  const testUrl = 'http://some.path.com';
  beforeAll(async () => {
    const module = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();

    appService = module.get<AppService>(AppService);
  });
  it('check getShortUrl', async () => {
    currCode = await appService.getShortUrl(testUrl);
    expect(currCode.trim().length).toBe(8);
  });
  it('check getFullUrl', async () => {
    const url = await appService.getFullUrl(currCode);
    expect(url.trim()).toBe(testUrl);
  });
});