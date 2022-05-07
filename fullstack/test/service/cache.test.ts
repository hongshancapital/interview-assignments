import mock, { MockApplication } from 'egg-mock';
import LRVCache from '../../app/utils/lru-cache';
import { assert } from 'egg-mock/bootstrap';

const cacheMap = {
  1: {
    id: 1,
    tinyUrl: '11111111',
    originalUrl: 'http://weiruifeng.com',
  },
  2: {
    id: 2,
    tinyUrl: '22222222',
    originalUrl: 'http://weiruifeng.com',
  },
  3: {
    id: 3,
    tinyUrl: '33333333',
    originalUrl: 'http://weiruifeng.com',
  },
  4: {
    id: 4,
    tinyUrl: '44444444',
    originalUrl: 'http://weiruifeng.com',
  },
};

describe('test/app/controller/url-map.test.ts', () => {
  let app: MockApplication;

  before(() => {
    // 创建当前应用的 app 实例
    app = mock.app();
    // 等待 app 启动成功，才能执行测试用例
    return app.ready();
  });

  afterEach(mock.restore);

  describe('cache-controller', () => {
    it('lru cache', async () => {
      const ctx = app.mockContext();
      const cacheService = ctx.service.cache;
      cacheService.client = LRVCache(3);
      const map = cacheService.client.showAll();
      assert(map.size === 0);
      cacheService.update({
        pid: 1,
        type: 'SET',
        key: '1',
        value: cacheMap[1],
      });
      assert(map.size === 1);
      cacheService.update({
        pid: 1,
        type: 'SET',
        key: '2',
        value: cacheMap[2],
      });
      assert(map.size === 2);
      cacheService.update({
        pid: 1,
        type: 'SET',
        key: '3',
        value: cacheMap[1],
      });
      assert(map.size === 3);
      assert(map.has('2'));
      cacheService.update({
        pid: 1,
        type: 'GET',
        key: '1',
      });
      cacheService.update({
        pid: 1,
        type: 'SET',
        key: '4',
        value: cacheMap[4],
      });
      assert(map.size === 3);
      assert(!map.has('2'));
      cacheService.update({
        pid: 1,
        type: 'DEL',
        key: '2',
      });
      assert(map.size === 3);
      cacheService.update({
        pid: 1,
        type: 'DEL',
        key: '3',
      });
      assert(map.size === 2);
    });
  });
});
