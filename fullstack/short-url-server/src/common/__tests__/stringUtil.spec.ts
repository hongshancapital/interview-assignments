import { string10to62, validURL } from '../stringUtil';

describe('AppController', () => {
  describe('root', () => {
    it('should convert 10-nim to 62-num"', async () => {
      expect(string10to62(763876481314)).toBe('ax9TQQd');
    });

    it('out of range 8bit"', async () => {
      expect(string10to62(7638764813141111111111111111111)).toBe('00');
    });

    it('should valid url"', async () => {
      expect(validURL('ssss')).toBe(false);
      expect(validURL('baidu.com')).toBe(true);
    });
  });
});
