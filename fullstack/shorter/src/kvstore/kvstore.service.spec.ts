import { CacheModule } from '@nestjs/common';
import { Test, TestingModule } from '@nestjs/testing';
import { KvstoreService } from './kvstore.service';

describe('KvstoreService', () => {
  let service: KvstoreService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [CacheModule.register<any>({})],
      providers: [KvstoreService],
    }).compile();

    service = module.get<KvstoreService>(KvstoreService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  it('kv worked', async () => {
    const kv = ['aaa', 'bbb'];
    await service.setKV(kv[0], kv[1]);
    const v = await service.getValue(kv[0]);
    expect(v).toBe(kv[1]);
  });
});
