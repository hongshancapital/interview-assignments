import { validURL } from '../stringUtil';

describe('AppController', () => {
  describe('root', () => {
    it('should valid url"', async () => {
      expect(validURL('ssss')).toBe(false);
      expect(validURL('baidu.com')).toBe(true);
    });
  });
});
