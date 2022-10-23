import { describe, expect, test, jest } from '@jest/globals';
import { getErrCode } from '../errHandler';
import { PARAM_ERROR, DEFAULT_CODE } from '../errCode';

const log = jest.spyOn(console, 'log').mockImplementation(() => {});

describe('errorHandler', () => {
  test('handle a promise rejection', () => {
    let errCode = getErrCode(PARAM_ERROR);
    expect(log).toBeCalledTimes(0);

    return expect(errCode).toBe(PARAM_ERROR);
  });

  test('occur a normal Error', () => {
    let errCode = getErrCode(new Error('occur'))
    expect(log).toBeCalledTimes(1);

    return expect(errCode).toBe(DEFAULT_CODE);
  });
});