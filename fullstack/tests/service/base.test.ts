import { ShortLinkParams, ShortLinkServiceBase } from '../../src/service/base';
import { expect } from 'chai';

class ShortLinkServiceBaseTest extends ShortLinkServiceBase {
  saveLink(link: string): string | Promise<string> {
    throw new Error('Method not implemented.');
  }
  getLink(id: string): string | Promise<string> {
    throw new Error('Method not implemented.');
  }
}

describe('ShortLinkServiceBase formatLink', () => {
  it('should return short link', () => {
    const params: ShortLinkParams = {
      host: 'sl-test.com',
    };
    const service = new ShortLinkServiceBaseTest(params);

    expect(service.formatLink('12345678')).equal('https://sl-test.com/12345678');
    expect(service.formatLink('')).equal('https://sl-test.com/');
    expect(service.formatLink('aaaaaaaaaa')).equal('https://sl-test.com/aaaaaaaaaa');
  });
});

describe('ShortLinkServiceBase parseShortLink', () => {
  it('should return parse result', () => {
    const params: ShortLinkParams = {
      host: 'sl-test.com',
    };
    const service = new ShortLinkServiceBaseTest(params);

    expect(service.parseShortLink('').error).equal('link is null');

    expect(service.parseShortLink('http://sl-test.com/123456789').error).equal('link format error');
    expect(service.parseShortLink('ftp://sl-test.com/?id=123456789').error).equal(
      'link format error',
    );

    expect(service.parseShortLink('https://sl-prod.com/12345678').error).equal('host is invalid');

    expect(service.parseShortLink('http://sl-test.com/12345678').error).undefined;
    expect(service.parseShortLink('https://sl-test.com/aaaaaaaa').error).undefined;
    expect(service.parseShortLink('http://sl-test.com/12345678').id).equals('12345678');
    expect(service.parseShortLink('https://sl-test.com/aaaaaaaa').id).equals('aaaaaaaa');
  });
});
