import { Test } from '@nestjs/testing';

import { SharedModule } from '../shared/shared.module';
import { DatabaseModule } from '../database/database.module';
import { ShortUrlService } from './short-url.service';
import { shortUrlProviders } from './short-url.providers';

describe('ShortService', () => {
  let service: ShortUrlService;
  beforeAll(async () => {
    const modRef = await Test.createTestingModule({
      imports: [DatabaseModule, SharedModule],
      providers: [ShortUrlService, ...shortUrlProviders],
    }).compile();
    service = modRef.get(ShortUrlService);
  });

  it('getCode', async () => {
    // 测试已存在的
    let url = 'http://coolcao.com/a';
    let code = await service.getCode(url);
    expect(code).toBe('1iII');

    // 测试未存在的
    url = 'http://coolcao.com/' + Math.floor(Math.random() * 100);
    code = await service.getCode(url);
    expect(code).toBeTruthy();
  });

  it('getUrl', async () => {
    // 测试已存在的
    let url = await service.getUrl('1iFN');
    expect(url).toBe('https://coolcao.com/e');

    // 测试未存在的
    url = await service.getUrl('aaa');
    expect(url).toBe('');
  });

  it('numToCode', () => {
    let num = 10000;
    const code = service.numToCode(num);
    expect(code).toContain('2Bi');

    num = -1;
    const toCode = () => {
      return service.numToCode(num);
    };
    expect(toCode).toThrowError();
  });

  it('codeToNum', () => {
    let code = '1iED';
    const num = service.codeToNum(code);
    console.log(num);
    expect(num).toBe(5000);

    code = '';
    const toNum = () => {
      return service.codeToNum(code);
    };
    expect(toNum).toThrowError();
  });
});
