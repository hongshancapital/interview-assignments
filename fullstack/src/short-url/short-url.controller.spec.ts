import { Test } from '@nestjs/testing';
import { DatabaseModule } from '../database/database.module';
import { SharedModule } from '../shared/shared.module';

import { ShortUrlController } from './short-url.controller';
import { ShortUrlService } from './short-url.service';
import { GetShortUrlParams, LongUrlDTO } from './short-url.dto';
import { shortUrlProviders } from './short-url.providers';

describe('ShortUrlController', () => {
  let controller: ShortUrlController;
  let shortUrlService: ShortUrlService;

  beforeAll(async () => {
    const modRef = await Test.createTestingModule({
      imports: [DatabaseModule, SharedModule],
      controllers: [ShortUrlController],
      providers: [ShortUrlService, ...shortUrlProviders],
    }).compile();
    controller = modRef.get(ShortUrlController);
    shortUrlService = modRef.get(ShortUrlService);
  });

  it('toShort', async () => {
    const code = Promise.resolve('1iED');
    jest.spyOn(shortUrlService, 'getCode').mockImplementation(() => code);
    const result = await controller.toShort(
      new LongUrlDTO('https://coolcao.com/d'),
    );
    expect(result).toBe('1iED');
  });

  it('getLong', async () => {
    const code = '1iED';
    const url = 'https://coolcao.com/d';
    jest.spyOn(shortUrlService, 'getUrl').mockImplementation(async () => url);
    const result = await controller.getLong(new GetShortUrlParams(code));
    expect(result).toBe('https://coolcao.com/d');
  });
});
