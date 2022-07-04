import { isValidUrl } from './reg';

describe('regular test', () => {
  it('should return an array of cats', async () => {
    const urls = [
      { url: 'http://www.baidu.com', valid: true },
      { url: 'https://www.baidu.com', valid: true },
      { url: 'mail://www.baidu.com.', valid: false },
      { url: 'ftp://www.baidu.com.', valid: false },
      { url: 'git://www.baidu.com.', valid: false },
      { url: 'ldap://www.baidu.com.', valid: false },
      { url: 'xxx://www.baidu.com.', valid: false },
      { url: 'fs.baidu.com.', valid: false },
      { url: 'nginx-server', valid: false },
      { url: 'http://%fdfss.sfsdf', valid: false },
      { url: 'http://api.baidu.com', valid: true },
      { url: 'http://api.baidu.com/4353', valid: true },
      {
        url: 'http://api.baidu.com/43345?from=3242&ryysg=533',
        valid: true,
      },
      { url: 'http://api.baidu.com/:dsaf$', valid: true },
    ];
    for (const url of urls) {
      expect(isValidUrl(url.url) === url.valid).toBeTruthy();
    }
  });
});
