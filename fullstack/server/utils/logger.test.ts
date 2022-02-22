import 'mocha';
import logger from './logger';

describe('logger 工具函数测试', function () {
  it('logger.error with one string params', function () {
    logger.error('logger.error test');
  })

  it('logger.log with one string params', function () {
    logger.log('logger.info test');
  })

  it('logger.debug with debug option', function () {
    logger.debug('logger.debug with show option', true);
  })

  it('logger.debug with only one string params', function () {
    logger.debug('logger.debug test');
  })
})
