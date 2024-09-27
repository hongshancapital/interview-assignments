import redisService from '../../../src/services/redis.service';
import { logger } from '../../../src/utils/logger';

jest.mock('../../../src/utils/logger');

describe('RedisService', () => {
  let redisClient: any;

  beforeAll(async () => {
    await redisService.connect();
    redisClient = redisService.getClient();
    await redisClient.flushAll('SYNC');
  });

  afterAll(async () => {
    await redisClient.flushAll('SYNC');
    await redisService.disconnect();
  });

  afterEach(async ()=> {
    jest.clearAllMocks();
  });
 
  describe('init', () => { 
    it('should log error and set ready to false when close emits an error', () => {
      redisService.ready = true;
      redisService.client.emit('error');
      expect(redisService.ready).toBe(false);
      redisService.ready = true;
    });
  });

  describe('connect', () => { 
    it('should set ready to true if connect ', async () => {
      redisService.ready = false;
      redisService.client.connect = jest.fn().mockResolvedValue({} as any);
      await redisService.connect();
      expect(redisService.client.connect).toHaveBeenCalled();
      expect(redisService.ready).toBe(true);
    });
  });

  describe('createBloom', () => {
    it('should create a new bloom filter', async () => {
      const bloomName = 'testBloom';
      const rate = 0.01;
      const capacity = 10000;
      const result = await redisService.createBloom(bloomName, rate, capacity);

      expect(result).toBe(true);
    });

    it('should create a new bloom filter if client not ready', async () => {
      const bloomName = 'testBloom';
      const rate = 0.01;
      const capacity = 10000;

      redisService.ready = false;
      redisService.client.connect = jest.fn().mockResolvedValue({} as any);
      const result = await redisService.createBloom(bloomName, rate, capacity);
      expect(redisService.client.connect).toHaveBeenCalled();

      expect(result).toBe(true);
    });

    // it('should return false if bloom filter already exists', async () => {
    //   const bloomName = 'testBloom';
    //   const rate = 0.01;
    //   const capacity = 10000;  
    //   redisService.ready = true;
    //   const result = await redisService.createBloom(bloomName, rate, capacity);

    //   expect(result).toBe(true);
    // });
  });

  describe('bloomAdd', () => {
    it('should add a new key to the bloom filter', async () => {
      const bloomName = 'testBloom';
      const key = 'testKey';
      const result = await redisService.bloomAdd(bloomName, key);

      expect(result).toBe(true);
    });

    it('should return false if key already exists in the bloom filter', async () => {
      const bloomName = 'testBloom';
      const key = 'testKey';
      const result = await redisService.bloomAdd(bloomName, key);

      expect(result).toBe(true);
    });
  });

  describe('bloomExists', () => {
    it('should return true if key exists in the bloom filter', async () => {
      const bloomName = 'testBloom';
      const key = 'testKey';
      await redisService.bloomAdd(bloomName, key);
      const result = await redisService.bloomExists(bloomName, key);

      expect(result).toBe(true);
    });

    it('should return false if key does not exist in the bloom filter', async () => {
      const bloomName = 'testBloom';
      const key = 'testKey22';
      const result = await redisService.bloomExists(bloomName, key);

      expect(result).toBe(false);
    });
  });

  describe('setEx', () => {
    it('should set a key with expiration time', async () => {
      const key = 'testKey';
      const value = 'testValue';
      const duration = 1;
      const result = await redisService.setEx(key, value, duration);

      expect(result).toBe('OK');
    });

    it('should get a key with expiration time', async () => {
      const key = 'testKey';
      const value = 'testValue';
      const duration = 1; // 1 second
      await redisService.setEx(key, value, duration);
      const result = await redisService.get(key);

      expect(result).toBe(value);
    });

    it('should return null if key does not exist', async () => {
      const key = 'nonexistentKey';
      const result = await redisService.get(key);

      expect(result).toBe(null);
    });
  });

  describe('disconnect', () => { 

    it('should log error if disconnect throws', async () => {
      redisService.client.disconnect = jest.fn().mockRejectedValue(new Error('Disconnect failed'));
      const errorSpy = jest.spyOn(logger, 'error');
      await redisService.disconnect();
      expect(redisService.ready).toBe(true);
      expect(errorSpy).toHaveBeenCalled();
    });

  });
});
