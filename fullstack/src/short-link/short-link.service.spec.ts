import { Test, TestingModule } from '@nestjs/testing';
import { ShortLinkService } from './short-link.service';
import { LinkEntity } from '../entities/link.entity';
import { TypeOrmModule } from '@nestjs/typeorm';
import { IdModule } from '../id/id.module';
import { RedisModule } from '@liaoliaots/nestjs-redis';
import { getRedisConfig, getDbConfig } from '../config';

describe('ShortLinkService', () => {
  let service: ShortLinkService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [
        TypeOrmModule.forFeature([LinkEntity]),
        TypeOrmModule.forRoot(getDbConfig()),
        RedisModule.forRoot(getRedisConfig()),
        IdModule,
      ],
      providers: [ShortLinkService],
    }).compile();

    service = module.get<ShortLinkService>(ShortLinkService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('short-link service redis case', () => {
    // 设置redis测试
    const timeSetExTimeOut = 6000;
    it(
      'set ex link ',
      async () => {
        const linkKey = `testLinkKey`,
          linkValue = `setValue`,
          ex = 1;
        expect(await service.setRedisLink(linkKey, linkValue, ex)).toBeTruthy();
        expect(
          (await service.getRedisLink(linkKey)) === linkValue,
        ).toBeTruthy();
        await service.wait(ex * 1000);
        expect(
          (await service.getRedisLink(linkKey)) === linkValue,
        ).not.toBeTruthy();
      },
      timeSetExTimeOut,
    );

    // 获取redis锁测试
    it('get lock', async () => {
      const lockKey = `testGetLock`,
        lockValue = `testGetLock`,
        ex = 5;
      expect(await service.getRedisLock(lockKey, lockValue, ex)).toBeTruthy();
      expect(
        await service.getRedisLock(lockKey, lockValue, ex),
      ).not.toBeTruthy();
    });

    // 删除redis锁测试
    it('del lock', async () => {
      const lockKey = `testDelLock1`,
        lockValue = `testDelLock1`,
        ex = 5;
      expect(await service.getRedisLock(lockKey, lockValue, ex)).toBeTruthy();
      expect(await service.delRedisLock(lockKey, lockValue)).toBeTruthy();
    });

    // 删除超时的redis锁测试
    const testDelLockTimeOut = 10000;
    it(
      'del expired lock ',
      async () => {
        const lockKey = `testDelLock2`,
          lockValue = `testDelLock2`,
          ex = 5;
        expect(await service.getRedisLock(lockKey, lockValue, ex)).toBeTruthy();
        const waitBef = Date.now();
        await service.wait(ex * 1000);
        const waitAft = Date.now();
        console.log('wait', waitAft, waitBef);
        expect(waitAft - waitBef - ex * 1000).toBeGreaterThanOrEqual(0);
        expect(await service.delRedisLock(lockKey, lockValue)).not.toBeTruthy();
      },
      testDelLockTimeOut,
    );
  });

  // 链接转换服务测试用例
  describe('short-link service case', () => {
    // 创建新链接
    it('should be created', async () => {
      const id = await service.createLink('https://www.google.com/fdsfs', 3);
      expect(id && typeof id === 'string' && id.length <= 8).toBeTruthy();
    });

    // 查找非法id的链接
    it('should not be got', async () => {
      const id = '123456789';
      const linkEntity = await service.findLinkById(id);
      expect(linkEntity === null).toBeTruthy();
    });

    // 先创建，创建成功后查找2次，第一次会从db查询，第二次会从redis查询
    it('should be got', async () => {
      const link = 'https://www.google.com/fdsfs';
      const id = await service.createLink(link, 3);
      expect(id && typeof id === 'string' && id.length <= 8).toBeTruthy();
      const { link: readLink } = await service.findLinkById(id);
      expect(readLink === link).toBeTruthy();
      const { link: readLink2 } = await service.findLinkById(id);
      expect(readLink2 === link).toBeTruthy();
    });

    // 对重复的id，设置重试次数1，可以再次生成id
    it('should be created in retry 1 times', async () => {
      const link = 'https://www.google.com/fdsfs';
      const id = await service.createLink(link, 1);
      expect(id && typeof id === 'string' && id.length <= 8).toBeTruthy();
      const newId = await service.insertLink(link, id, 1);
      expect(id !== newId).toBeTruthy();
    });

    // 对重复的id，设置重试次数0，不可以再次生成id
    it('should be created in retry 1 times', async () => {
      const link = 'https://www.google.com/fdsfs';
      const id = await service.createLink(link, 1);
      expect(id && typeof id === 'string' && id.length <= 8).toBeTruthy();
      try {
        const newId = await service.insertLink(link, id, 0);
        expect(id !== newId).not.toBeTruthy();
      } catch (error) {
        expect(error !== null).toBeTruthy();
      }
    });

    // 对错误的id，不能插入数据库
    it('should not be created in retry 0 time', async () => {
      const link = 'https://www.google.com/fdsfs';
      const errorId = '13424224342';
      try {
        const id = await service.insertLink(link, errorId, 0);
        expect(id !== errorId).not.toBeTruthy();
      } catch (error) {
        expect(error !== null).toBeTruthy();
      }
    });

    // 先创建id，并发根据id读取链接，都会成功
    it('should be got concurrently', async () => {
      const link = 'https://www.google.com/fdsfs';
      const id = await service.createLink(link, 3);
      expect(id && typeof id === 'string' && id.length <= 8).toBeTruthy();
      console.log('concurrently1', Date.now());
      service.findLinkById(id).then(({ link: readLink }) => {
        console.log('concurrently2', Date.now(), readLink);
        expect(readLink === link).toBeTruthy();
      });
      const { link: readLink } = await service.findLinkById(id);
      expect(readLink === link).toBeTruthy();
    });
  });
});
