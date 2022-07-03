import { Test, TestingModule } from '@nestjs/testing';
import { IdService } from './id.service';

describe('IdService', () => {
  let service: IdService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [IdService],
    }).compile();

    service = module.get<IdService>(IdService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('IdService nextId()', () => {
    const times = 1000;
    it(`id should be unique in ${times} times`, () => {
      const ids = [];
      for (let i = 0; i < times; i++) {
        const nextId = service.nextId();
        expect(nextId.length).toBeLessThanOrEqual(8);
        const unique = !ids.includes(nextId);
        expect(unique).toBeTruthy();
        ids.push(nextId);
      }
    });
    const rate = 1000;
    it(`${rate} ids generated in 1s`, () => {
      const startTime = BigInt(Date.now());
      for (let i = 0; i < rate; i++) {
        service.nextId();
      }
      const endTime = BigInt(Date.now());
      const useTime = endTime - startTime;
      console.log(`1000 ids generated in ${useTime} ms`);
      expect(useTime).toBeLessThan(rate);
    });
  });
});
