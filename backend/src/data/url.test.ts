import { getOriginUrlFromCache, memoryCacheMissing, newUrl } from './url'; // 请将路径替换为实际的模块路径
import { ErrorUrlCreatorTooLong, ErrorUrlDescriptionTooLong, ErrorUrlOriginNotFound, ErrorUrlOriginUrlTooLong, ErrorUrlRequiredCreator, ErrorUrlRequiredDescription, ErrorUrlRequiredOriginUrl, ErrorUrlShouldProvide } from '../biz/errors/url'; // 请根据实际路径导入错误
import UrlModel, { UrlStatusOpen } from './models/url'; // 请根据实际路径导入 UrlModel
import memoryCache from "./urlcache"
import testSetup from '../../test/tests_setup/testSetup';
import testTeardown from '../../test/tests_setup/testTearDown';

describe('getOriginUrlFromCache', () => {
    jest.mock('../models/url'); // 使用 Jest 模拟 UrlModel

    it('should return origin URL from cache when available', async () => {
        const cache = await memoryCache;
        const shortUrl = 'shorturl123';
        const cachedOriginUrl = 'cachedOriginUrl';
        await cache.set(shortUrl, cachedOriginUrl);

        const urlDataMock = {
            originUrl: 'https://example.com',
            status: UrlStatusOpen,
            expires: undefined,
        };

        // 使用 jest.fn() 创建一个模拟的 findOneAndUpdate 函数
        const findOneMock = jest.fn(() => ({
            select: jest.fn().mockResolvedValue(urlDataMock) // 模拟 select() 方法
        }));
        (UrlModel.findOne as jest.Mock) = findOneMock;

        try {
            const result = await getOriginUrlFromCache(shortUrl);
            expect(result).toBe(cachedOriginUrl);
        } finally {
            await cache.del(shortUrl);
        }
    });

    it('should fetch origin URL from database when not in cache', async () => {
        const cache = await memoryCache;
        const shortUrl = 'shorturl123';
        const originUrl = 'https://example.com';

        const urlDataMock = {
            originUrl: originUrl,
            status: UrlStatusOpen,
            expires: undefined,
        };

        // 使用 jest.fn() 创建一个模拟的 findOneAndUpdate 函数
        const findOneMock = jest.fn(() => ({
            select: jest.fn().mockResolvedValue(urlDataMock) // 模拟 select() 方法
        }));
        (UrlModel.findOne as jest.Mock) = findOneMock;

        try {
            expect(await cache.get(shortUrl)).toBeUndefined();
            const result = await getOriginUrlFromCache(shortUrl);
            expect(result).toBe(originUrl);
            expect(await cache.get(shortUrl)).toBe(originUrl);
        } finally {
            await cache.del(shortUrl);
        }
    });

    it('should handle missing short URL in cache and database', async () => {
        const shortUrl = 'missingurl';

        const urlDataMock = null;

        // 使用 jest.fn() 创建一个模拟的 findOneAndUpdate 函数
        const findOneMock = jest.fn(() => ({
            select: jest.fn().mockResolvedValue(urlDataMock) // 模拟 select() 方法
        }));
        (UrlModel.findOne as jest.Mock) = findOneMock;
        await expect(getOriginUrlFromCache(shortUrl)).rejects.toThrow(ErrorUrlOriginNotFound);
    });

    it('should handle expired URL in cache and database', async () => {
        const cache = await memoryCache;
        const shortUrl = 'expiredurl';
        const originUrl = 'https://example.com';

        const urlDataMock = {
            originUrl: originUrl,
            status: UrlStatusOpen,
            expires: new Date(2000, 0, 1), // 过期日期设置为过去的日期
        };

        // 使用 jest.fn() 创建一个模拟的 findOneAndUpdate 函数
        const findOneMock = jest.fn(() => ({
            select: jest.fn().mockResolvedValue(urlDataMock) // 模拟 select() 方法
        }));
        (UrlModel.findOne as jest.Mock) = findOneMock;

        try {
            expect(await cache.get(shortUrl)).toBeUndefined();
            await expect(getOriginUrlFromCache(shortUrl)).rejects.toThrow(ErrorUrlOriginNotFound);
            expect(await cache.get(shortUrl)).toBe(memoryCacheMissing);
        } finally {
            await cache.del(shortUrl);
        }
    });
    jest.unmock('../models/url');
});


describe('newUrl', () => {
    beforeAll(testSetup)
    afterAll(testTeardown)
    it('should create a new URL', async () => {
      const newUrlRequest = {
        originUrl: 'https://example.com',
        description: 'Test URL',
        creator: 'Test User',
      };
  
      // 调用 newUrl 函数
      const createdUrl = await newUrl(newUrlRequest);
  
      // 执行断言，检查创建是否成功
      expect(createdUrl).toBeDefined();
      expect(createdUrl?.originUrl).toBe(newUrlRequest.originUrl);
    });
  });

describe('newUrl edgecases', () => {    
    it('should throw an error if originUrl is missing', async () => {
        // Set up missing originUrl
        const newUrlRequest = {
          originUrl: '', // Missing originUrl
          description: 'Test URL',
          creator: 'Test User',
        };
    
        // Call the function and expect it to throw an error
        await expect(newUrl(newUrlRequest)).rejects.toThrow(ErrorUrlRequiredOriginUrl);
      });
    
      it('should throw an error if originUrl exceeds maxLength', async () => {
        // Set up originUrl that exceeds maxLength
        const newUrlRequest = {
          originUrl: 'https://example.com'.repeat(100), // Exceeds maxLength
          description: 'Test URL',
          creator: 'Test User',
        };
    
        // Call the function and expect it to throw an error
        await expect(newUrl(newUrlRequest)).rejects.toThrow(ErrorUrlOriginUrlTooLong);
      });
    
      it('should throw an error if description is missing', async () => {
        // Set up missing description
        const newUrlRequest = {
          originUrl: 'https://example.com',
          description: '', // Missing description
          creator: 'Test User',
        };
    
        // Call the function and expect it to throw an error
        await expect(newUrl(newUrlRequest)).rejects.toThrow(ErrorUrlRequiredDescription);
      });
    
      it('should throw an error if description exceeds maxLength', async () => {
        // Set up description that exceeds maxLength
        const newUrlRequest = {
          originUrl: 'https://example.com',
          description: 'Test Description'.repeat(100), // Exceeds maxLength
          creator: 'Test User',
        };
    
        // Call the function and expect it to throw an error
        await expect(newUrl(newUrlRequest)).rejects.toThrow(ErrorUrlDescriptionTooLong);
      });
    
      it('should throw an error if creator is missing', async () => {
        // Set up missing creator
        const newUrlRequest = {
          originUrl: 'https://example.com',
          description: 'Test URL',
          creator: '', // Missing creator
        };
    
        // Call the function and expect it to throw an error
        await expect(newUrl(newUrlRequest)).rejects.toThrow(ErrorUrlRequiredCreator);
      });
    
      it('should throw an error if creator exceeds maxLength', async () => {
        // Set up creator that exceeds maxLength
        const newUrlRequest = {
          originUrl: 'https://example.com',
          description: 'Test URL',
          creator: 'Test User'.repeat(10), // Exceeds maxLength
        };
    
        // Call the function and expect it to throw an error
        await expect(newUrl(newUrlRequest)).rejects.toThrow(ErrorUrlCreatorTooLong);
      });
});