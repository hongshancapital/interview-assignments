import { Test } from '@nestjs/testing';
import { isValideUrl, EncodeUrl } from './mapper';


describe('Test mapper', () => {
  const url1 = 'http://some.path.com';
  const url2 = "www.baidu.com";
  const url3 = "http://192.168.0.110:9494/?folder=/srv/dev-disk-by-uuid-b66ac6ca-27d2-4aee-84fc-fe15b8f2f470/workspace/short_domain_mapper";
  const url4 = "http://192.168.0.110:3000/full_url?short_url=%2Bz7sLRvN%20%20";
 
  it('check isValideUrl', async () => {
    expect(isValideUrl(url1)).toBe(true);
    expect(isValideUrl(url2)).toBe(true);
    expect(isValideUrl(url3)).toBe(true);
    expect(isValideUrl(url4)).toBe(true);
  });

  it('check EncodeUrl', async () => {
    let encoded = EncodeUrl(url1)
    expect(encoded["short_url"].length).toBe(8);
    encoded = EncodeUrl(url2)
    expect(encoded["short_url"].length).toBe(8);
    encoded = EncodeUrl(url3)
    expect(encoded["short_url"].length).toBe(8);
    encoded = EncodeUrl(url4)
    expect(encoded["short_url"].length).toBe(8);
  });
});