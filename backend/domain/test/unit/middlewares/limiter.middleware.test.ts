import { NextFunction, Request, Response } from 'express';
import { rate, TokenBucket } from '../../../src/middlewares/limiter.middleware';

describe('Middleware: rateLimiter', () => {
  let req: Partial<Request>;
  let res: Partial<Response>;
  let next: NextFunction;

  beforeEach(() => {
    req = {};
    res = {
      status: jest.fn(() => res as Response),
      json: jest.fn(),
    };
    next = jest.fn();
  });

  it('should call next if there are enough tokens in the bucket', () => {
    rate(req as Request, res as Response, next);

    expect(next).toHaveBeenCalled();
    expect(res.status).not.toHaveBeenCalled();
  });

  it('should return 429 if there are not enough tokens in the bucket', () => {
    jest.spyOn(TokenBucket.prototype, 'getToken').mockReturnValueOnce(false);
    rate(req as Request, res as Response, next);

    expect(next).not.toHaveBeenCalled();
    expect(res.status).toHaveBeenCalledWith(429);
    expect(res.json).toHaveBeenCalledWith({ status: 429, message: 'Too Many Requests'});
  });

});
