import { Request, Response } from 'express';
import redisService from '../../../src/services/redis.service';
import { isBlack, RATE_LIMIT_MAX_REQUESTS, DAILY_TOTAL_LIMIT_MAX_REQUESTS } from '../../../src/middlewares/black.middleware';

describe('Middleware: isBlack', () => {
  let req: Request;
  let res: Response;
  let next: jest.Mock;
  let redisClient: any;
  const mockRequest = {
    ip: '127.0.0.1',
  };
  
  const mockResponse = () => {
    const res: Partial<Response> = {};
    res.status = jest.fn().mockReturnValue(res);
    res.json = jest.fn().mockReturnValue(res);
    return res as Response;
  };

  beforeAll(async () => {
    await redisService.connect();
    redisClient = redisService.getClient();
  });

  beforeEach(() => {
    req = mockRequest as Request;
    res = mockResponse();
    next = jest.fn();
  });

  afterEach(async () => {
    await redisClient.flushDb('SYNC' as any);
  });

  afterAll(async () => {
    await redisService.disconnect();
  });

  it('should pass the request if the ip is not blacklisted and the number of requests is within the limit', async () => {
    for (let i = 0; i < RATE_LIMIT_MAX_REQUESTS - 1; i++) {
      await isBlack(req, res, next);
    }

    expect(res.status).not.toHaveBeenCalled();
    expect(res.json).not.toHaveBeenCalled();
    expect(next).toHaveBeenCalledTimes(RATE_LIMIT_MAX_REQUESTS - 1);
  });

  it('should pass the request if the user is not blacklisted and the number of requests is within the limit', async () => {

    const mockRequest: any = {
      ip: '127.0.0.1',
      user: {
        uid: '121212',
      }
    } ;
    for (let i = 0; i < RATE_LIMIT_MAX_REQUESTS - 1; i++) {
      await isBlack(mockRequest, res, next);
    }

    expect(res.status).not.toHaveBeenCalled();
    expect(res.json).not.toHaveBeenCalled();
    expect(next).toHaveBeenCalledTimes(RATE_LIMIT_MAX_REQUESTS - 1);
  });

  it('should blacklist the user if the number of requests exceeds the limit', async () => {
    for (let i = 0; i <= RATE_LIMIT_MAX_REQUESTS; i++) {
      await isBlack(req, res, next);
    }

    expect(res.status).toHaveBeenCalledWith(403);
    expect(res.json).toHaveBeenCalledWith({ message: "Forbidden.", status: 403 });
    expect(next).not.toHaveBeenCalledTimes(RATE_LIMIT_MAX_REQUESTS+1);
  });

  it('should return 403 if the user is blacklisted', async () => {
    const key = `${req.ip}:undefined`;
    await redisClient.set(redisService.getKey(`black:${key}`), 1);

    await isBlack(req, res, next);

    expect(res.status).toHaveBeenCalledWith(403);
    expect(res.json).toHaveBeenCalledWith({ message: "Forbidden.", status: 403 });
    expect(next).not.toHaveBeenCalled();
  });

  it('should return 429 if the user daily requests max', async () => {
    const key = `${req.ip}:undefined`;
    await redisClient.set(redisService.getKey(`requests:total:${key}`), DAILY_TOTAL_LIMIT_MAX_REQUESTS);
    await isBlack(req, res, next);

    expect(res.status).toHaveBeenCalledWith(429);
    expect(res.json).toHaveBeenCalledWith({ message: "Too Many Requests Tody.", status: 429 });
    expect(next).not.toHaveBeenCalled();
  });

});