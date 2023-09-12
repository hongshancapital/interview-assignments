import { setupTraceId, getTraceId } from '../../../src/utils/logger';
describe('utils: logger', () => {

  describe('setupTraceId', () => {
    it('should setup trace id correctly', () => {
      const traceId = '1234';
      const result = setupTraceId(traceId);
      expect(result).toEqual(traceId);
    });
  });

  describe('getTraceId', () => {
    it('should return trace id correctly', () => {
      const traceId = '1234';
      setupTraceId(traceId);
      const result = getTraceId();
      expect(result).toEqual(traceId);
    });
  });
  
});
