import { joinUrl } from './path'; // 请将路径替换为实际的模块路径

describe('joinUrl', () => {
  it('should join URL correctly with https', () => {
    const https = true;
    const host = 'example.com';
    const shortUrl = 'abc123';
    const result = joinUrl(https, host, shortUrl);
    expect(result).toBe('https://example.com/url/abc123');
  });

  it('should join URL correctly with http', () => {
    const https = false;
    const host = 'example.org';
    const shortUrl = 'xyz789';
    const result = joinUrl(https, host, shortUrl);
    expect(result).toBe('http://example.org/url/xyz789');
  });

  it('should handle empty host', () => {
    const https = true;
    const host = '';
    const shortUrl = '12345';
    const result = joinUrl(https, host, shortUrl);
    expect(result).toBe('https:///url/12345');
  });

  it('should handle empty shortUrl', () => {
    const https = false;
    const host = 'example.net';
    const shortUrl = '';
    const result = joinUrl(https, host, shortUrl);
    expect(result).toBe('http://example.net/url/');
  });
});