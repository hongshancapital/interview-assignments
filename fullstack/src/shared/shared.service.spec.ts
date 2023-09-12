import { Test } from '@nestjs/testing';
import { SharedService } from './shared.service';

describe('ShortService', () => {
  let service: SharedService;
  beforeAll(async () => {
    const modRef = await Test.createTestingModule({
      providers: [SharedService],
    }).compile();
    service = modRef.get(SharedService);
  });

  it('crc32', () => {
    let str = '';
    let num = service.crc32(str);
    expect(num).toBe(0);

    str = 'coolcao';
    num = service.crc32(str);
    expect(num).toBe(1601753080);
  });

  it('to62Str', () => {
    let num = 50000;
    const str = service.to62Str(num);
    console.log(str);
    expect(str).toBe('d0s');

    num = -1;
    const toStr = () => {
      return service.to62Str(num);
    };
    expect(toStr).toThrowError();
  });

  it('strToNum', () => {
    let str = 'd0s';
    const num = service.strToNum(str);
    expect(num).toBe(50000);

    str = '***';
    const toNum = () => {
      return service.strToNum(str);
    };
    expect(toNum).toThrowError();
  });

});
