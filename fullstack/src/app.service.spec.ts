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
  it('check getShortCode', async () => {
    currCode = await appService.getShortCode(testUrl);
    expect(currCode.length).toBe(6);
  });
  it('check getLongUrl', async () => {
    const url = await appService.getLongUrl(currCode);
    expect(url).toBe(testUrl);
  });
  it('check remove test code', async () => {
    const affected = await appService.removeShortCode(currCode);
    expect(affected).toBe(1);
  });
});
