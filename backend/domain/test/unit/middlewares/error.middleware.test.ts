import { errorHandler } from '../../../src/middlewares/error.middleware';
import { Request, Response, NextFunction } from 'express';
import { ApplicationError } from '../../../src/helpers/application.err';
import { logger } from '../../../src/utils/logger';

describe('Middleware: errorHandler', () => {
  const req = {} as Request;
  const res = {
    status: jest.fn().mockReturnThis(),
    json: jest.fn(),
  } as unknown as Response;
  const next = jest.fn() as unknown as NextFunction;
  const error = new ApplicationError(400, 'Error message');

  beforeEach(() => {
    jest.spyOn(logger, 'error').mockImplementation();
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('should return a response with a status and message', async () => {
    await errorHandler(error, req, res, next);

    expect(res.status).toHaveBeenCalledWith(400);
    expect(res.json).toHaveBeenCalledWith({
      status: 400,
      message: 'Error message',
    });
    expect(logger.error).toHaveBeenCalled();
  });

  it('should return a default message if error.message is not provided', async () => {
    const error = new ApplicationError(400, '');

    await errorHandler(error, req, res, next);

    expect(res.status).toHaveBeenCalledWith(400);
    expect(res.json).toHaveBeenCalledWith({
      status: 400,
      message: 'Something went wrong',
    });
    expect(logger.error).toHaveBeenCalled();
  });

  it('should return a default status if error.status is not provided', async () => {
    const error = new ApplicationError(0, 'Error message');

    await errorHandler(error, req, res, next);

    expect(res.status).toHaveBeenCalledWith(500);
    expect(res.json).toHaveBeenCalledWith({
      status: 500,
      message: 'Error message',
    });
    expect(logger.error).toHaveBeenCalled();
  });

  it('should call the next middleware', async () => {
    await errorHandler(error, req, res, next);

    expect(next).not.toHaveBeenCalled();
  });

});
