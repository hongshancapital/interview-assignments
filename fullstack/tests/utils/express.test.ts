import { successBody, errorBody } from '../../src/utils/express';
import { expect } from 'chai';

describe('Utils express successBody', () => {
  it('should return success true', () => {
    expect(successBody('true').success).equal(true);
    expect(successBody({ a: '1' }).success).equal(true);
    expect(successBody('https://sl.cn/12345678').success).equal(true);
    expect(successBody(false).success).equal(true);
    expect(successBody(1).success).equal(true);
  });

  it('should return errCode errMsg empty', () => {
    expect(successBody('true').errCode).undefined;
    expect(successBody('true').errMsg).undefined;
    expect(successBody({ a: '1' }).errCode).undefined;
    expect(successBody({ a: '1' }).errMsg).undefined;
    expect(successBody('https://sl.cn/12345678').errCode).undefined;
    expect(successBody('https://sl.cn/12345678').errMsg).undefined;
    expect(successBody(false).errCode).undefined;
    expect(successBody(false).errMsg).undefined;
    expect(successBody(1).errCode).undefined;
    expect(successBody(1).errMsg).undefined;
  });

  it('should return data equal input', () => {
    expect(successBody('true').data).equal('true');
    expect(successBody({ a: '1' }).data?.a).equal('1');
    expect(successBody('https://sl.cn/12345678').data).equal('https://sl.cn/12345678');
    expect(successBody(false).data).equal(false);
    expect(successBody(1).data).equal(1);
  });
});

describe('Utils express errorBody', () => {
  it('should return success false', () => {
    expect(errorBody('1', 'error').success).equal(false);
  });

  it('should return data empty', () => {
    expect(errorBody('1', 'error').data).undefined;
  });

  it('should return errCode, errMsg equal input', () => {
    expect(errorBody('1', 'error').errCode).equal('1');
    expect(errorBody('1', 'error').errMsg).equal('error');
  });
});
