import { Request, Response } from 'express';
import {
  createRequest, createResponse, MockRequest, MockResponse,
} from 'node-mocks-http';
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

  it('check getUrl param', async () => {
    req = createRequest({
      method: 'GET',
      url: '/',
    });
    req.params.short = '';
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
  it('check setUrl param', async () => {
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
  it('someone else set new url at the same time', async () => {
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
        find: jest.fn().mockImplementation(() => Promise.resolve([]))
      })),
      save: jest.fn().mockImplementation(() => {
        req.db.getRepository = jest.fn().mockImplementation(() => ({
          find: jest.fn().mockImplementation(() => Promise.resolve([1]))
        }));
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
});
