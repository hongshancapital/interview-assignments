import { asyncHook, TRACE_ID_HEADER } from '../../../src/middlewares/asyncHook.middleware';
import * as logger from '../../../src/utils/logger';
import { v4 as uuidV4 } from 'uuid';

jest.mock("uuid");
describe('Middleware: asyncHook', () => {
  let req: any, res: any, next: jest.Mock;
  const TRACE_ID = logger.TRACE_ID;
  
  beforeEach(() => {
    req = {
      header: jest.fn().mockReturnValue(null),
    };
    res = {
      setHeader: jest.fn(),
    };
    next = jest.fn();
  });
  
  afterEach(() => {
    jest.restoreAllMocks();
  });

  afterAll(() => {
    jest.resetAllMocks();
  });
  
  it('should set trace id in request and response headers if not present in request', async () => {
    const traceId = '1234';
    (uuidV4 as jest.Mock).mockReturnValue(traceId);
    jest.spyOn(logger, 'setupTraceId').mockReturnValue(traceId);
  
    await asyncHook(req, res, next);

    expect(req[TRACE_ID]).toBe(traceId);
    expect(res.setHeader).toHaveBeenCalledWith(TRACE_ID_HEADER, traceId);
    expect(logger.setupTraceId).toHaveBeenCalledWith(traceId);
    expect(next).toHaveBeenCalled();
  });
  
  it('should set trace id in request and response headers if present in request', async () => {
    const traceId = 'abcd';
    req.header.mockReturnValue(traceId);
    jest.spyOn(logger, 'setupTraceId').mockReturnValue(traceId);

    await asyncHook(req, res, next);

    expect(req[TRACE_ID]).toBe(traceId);
    expect(res.setHeader).toHaveBeenCalledWith(TRACE_ID_HEADER, traceId);
    expect(logger.setupTraceId).toHaveBeenCalledWith(traceId);
    expect(next).toHaveBeenCalled();
  });

});