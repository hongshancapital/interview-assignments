import { Request, Response } from 'express';
import {
  createRequest, createResponse, MockRequest, MockResponse,
} from 'node-mocks-http';
import { NOT_EXIST_URL } from '../src/util.js';
import encoder from '../src/controller/encoder.js';

describe('controller test', () => {

  let req: MockRequest<Request>;
  let res: MockResponse<Response>;

  // Act before assertions
  beforeAll(async () => {
    res = createResponse();
  });

  // Teardown (cleanup) after assertions
  afterAll(() => {
    jest.unmock('hashids');
  });

  it('getUrl by empty short url', async () => {
    req = createRequest({
      method: 'GET',
      url: '/',
    });
    req.params.short = '';
    const result = await encoder.getUrl(req, res);
    expect(result.isSuccess).toBe(false);
  });
  it('getUrl by not exist short url', async () => {
    req = createRequest({
      method: 'GET',
      url: '/',
    });
    req.params.short = NOT_EXIST_URL;
    const result = await encoder.getUrl(req, res);
    expect(result.isSuccess).toBe(false);
  });
  it('getUrl from redis', async () => {
    req = createRequest({
      method: 'GET',
      url: '/abcde',
    });
    req.params.short = 'abcde';
    const longUrl = 'http://www.example.com';
    req.redisClient = {
      get: jest.fn().mockImplementation(() => Promise.resolve(longUrl))
    };
    const result = await encoder.getUrl(req, res);
    expect(result.isSuccess).toBe(true);
    expect(result.data).toBe(longUrl);
  });
  it('getUrl from database', async () => {
    req = createRequest({
      method: 'GET',
      url: '/abcde',
    });
    req.params.short = 'abcde';
    const longUrl = 'http://www.example.com';
    req.redisClient = {
      get: jest.fn().mockImplementation(() => Promise.resolve('')),
      set: jest.fn().mockImplementation(() => '')
    };
    req.db = {
      getRepository: jest.fn().mockImplementation(() => ({
        find: jest.fn().mockImplementation(() => Promise.resolve([{ original: longUrl }]))
      }))
    };
    // mock hashids
    jest.mock('hashids');
    const result = await encoder.getUrl(req, res);
    expect(result.isSuccess).toBe(true);
    expect(result.data).toBe(longUrl);
    expect(req.db.getRepository).toHaveBeenCalledTimes(1);
    expect(req.redisClient.set).toHaveBeenLastCalledWith(req.params.short, longUrl);
  });
  it('getUrl from database and return empty', async () => {
    req = createRequest({
      method: 'GET',
      url: '/abcde',
    });
    req.params.short = 'abcde';
    req.redisClient = {
      get: jest.fn().mockImplementation(() => Promise.resolve('')),
      set: jest.fn().mockImplementation(() => '')
    };
    req.db = {
      getRepository: jest.fn().mockImplementation(() => ({
        find: jest.fn().mockImplementation(() => Promise.resolve([]))
      }))
    };
    // mock hashids
    jest.mock('hashids');
    const result = await encoder.getUrl(req, res);
    expect(result.isSuccess).toBe(false);
    expect(req.db.getRepository).toHaveBeenCalledTimes(1);
    expect(req.redisClient.set).toHaveBeenCalledTimes(0);
  });
  it('setUrl by empty long url', async () => {
    req = createRequest({
      method: 'POST',
      url: '/encode',
      body: {
        url: ''
      }
    });
    const result = await encoder.setUrl(req, res);
    expect(result.isSuccess).toBe(false);
  });
  it('setUrl normally', async () => {
    req = createRequest({
      method: 'POST',
      url: '/encode',
      body: {
        url: 'http://www.example.com'
      }
    });
    req.db = {
      getRepository: jest.fn().mockImplementation(() => ({
        find: jest.fn().mockImplementation(() => Promise.resolve([]))
      })),
      save: jest.fn().mockImplementation(() => Promise.resolve([{ id: 1}])),
      create: jest.fn().mockImplementation(() => Promise.resolve({}))
    };
    const result = await encoder.setUrl(req, res);
    expect(result.isSuccess).toBe(true);
    expect(result.data).toBe('XE');
    expect(req.db.save).toHaveBeenCalledTimes(1);
    expect(req.db.create).toHaveBeenCalledTimes(1);
  });
  it('setUrl where url existed', async () => {
    const longUrl = 'http://www.example.com';
    req = createRequest({
      method: 'POST',
      url: '/encode',
      body: {
        url: longUrl
      }
    });
    req.db = {
      getRepository: jest.fn().mockImplementation(() => ({
        find: jest.fn().mockImplementation(() => Promise.resolve([{ id: 1 }]))
      })),
      save: jest.fn().mockImplementation(() => Promise.resolve([{ id: 1}])),
      create: jest.fn().mockImplementation(() => Promise.resolve({}))
    };
    const result = await encoder.setUrl(req, res);
    expect(result.isSuccess).toBe(true);
    expect(result.data).toBe('XE');
    expect(req.db.save).toHaveBeenCalledTimes(0);
    expect(req.db.create).toHaveBeenCalledTimes(0);
  });
  it('concurrently seturl with same url and success', async () => {
    const longUrl = 'http://www.example.com';
    req = createRequest({
      method: 'POST',
      url: '/encode',
      body: {
        url: longUrl
      }
    });
    req.db = {
      getRepository: jest.fn().mockImplementation(() => ({
        // 模拟初次并发插入新url，反馈空
        find: jest.fn().mockImplementation(() => Promise.resolve([]))
      })),
      save: jest.fn().mockImplementation(() => {
        req.db.getRepository = jest.fn().mockImplementation(() => ({
          // 模拟再次查询返回成功结果
          find: jest.fn().mockImplementation(() => Promise.resolve([1]))
        }));
        // 模拟触发违反字段唯一约束
        return Promise.reject(new Error('colorm unique error'))
      }),
      create: jest.fn().mockImplementation(() => Promise.resolve({}))
    };
    const result = await encoder.setUrl(req, res);
    expect(result.isSuccess).toBe(true);
    expect(result.data).toBe('XE');
    expect(req.db.save).toHaveBeenCalledTimes(1);
    expect(req.db.create).toHaveBeenCalledTimes(1);
  });
  it('concurrently seturl with same url and failure', async () => {
    const longUrl = 'http://www.example.com';
    req = createRequest({
      method: 'POST',
      url: '/encode',
      body: {
        url: longUrl
      }
    });
    req.db = {
      getRepository: jest.fn().mockImplementation(() => ({
        // 模拟初次并发插入新url，反馈空
        find: jest.fn().mockImplementation(() => Promise.resolve([]))
      })),
      save: jest.fn().mockImplementation(() => {
        req.db.getRepository = jest.fn().mockImplementation(() => ({
          // 模拟再次查询返回结果为空
          find: jest.fn().mockImplementation(() => Promise.resolve([]))
        }));
        // 模拟触发违反字段唯一约束
        return Promise.reject(new Error('colorm unique error'))
      }),
      create: jest.fn().mockImplementation(() => Promise.resolve({}))
    };
    const result = await encoder.setUrl(req, res);
    expect(result.isSuccess).toBe(false);
    expect(req.db.save).toHaveBeenCalledTimes(1);
    expect(req.db.create).toHaveBeenCalledTimes(1);
  });
});
