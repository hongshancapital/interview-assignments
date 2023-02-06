import Redis from 'ioredis-mock';
import { SinonSandbox } from 'sinon';
import RedisFactory from '../../src/cache/RedisFactory';

export function mockRedis(sandbox: SinonSandbox) {
  const mockRedisClient = new Redis();
  sandbox.stub(RedisFactory, 'getClient').callsFake(() => {
    console.log("Use mock redis to replace external dependencies redis");
    return mockRedisClient;
  })
}
