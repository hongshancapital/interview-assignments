import * as request from 'supertest';
import { initTestApp } from '../src/utils';
import { genSUrlToken } from '../src/sUrl';
require('dotenv').config();

describe('短网址请求跳转', () => {
  const { app, redis } = initTestApp({});
  const token = genSUrlToken('https://www.baidu.com');
  beforeAll(async () => {
    await redis.hset(token, { u: 'www.baidu.com', p: 1 });
  });

  it('请求存在的短网址，跳转到短网址成功', async () => {
    const response = await request(app).get(`/${token}`);

    expect(response.status).toBe(302);
    expect(response.headers.location).toBe('https://www.baidu.com');
  });

  it('请求不存在的短网址，跳转到配置中的地址', async () => {
    const response = await request(app).get('/test');

    expect(response.status).toBe(302);
    expect(response.headers.location).toBe(process.env.BASE_URL);
  });
});

describe('申请短网址', () => {
  const { app, redis } = initTestApp({});

  beforeEach(async () => {
    await redis.flushall();
  });

  it('请求不被支持的url', async () => {
    const response = await request(app).post('/').send({ url: 'ftp://www.ftp.com' });
    expect(response.status).toBe(400);
  });

  it('请求错误的url参数', async () => {
    const response = await request(app).post('/').send({ url: 'xxxxx' });
    expect(response.status).toBe(400);
  });

  it('请求不存在的短网址，生成新的短网址。', async () => {
    const response = await request(app).post('/').send({ url: 'https://www.google.com' });

    expect(response.status).toBe(200);
    expect(response.body).toEqual({
      sUrl: expect.any(String),
      expireIn: expect.any(Number),
    });
  });

  it('请求已存在的短网址，为其续命并返回', async () => {
    const url = 'https://www.baidu.com';
    const token = genSUrlToken('https://www.baidu.com');
    await redis.hset(token, { u: 'www.baidu.com', p: 1 });

    const response = await request(app).post('/').send({ url });

    expect(response.status).toBe(200);
    expect(response.body).toEqual({
      sUrl: token,
      expireIn: expect.any(Number),
    });
  });

  it('请求不存在的短网址，发生冲突，为其扩展为其他的token', async () => {
    const url = 'https://www.baidu.com';
    const token = genSUrlToken('https://www.baidu.com');
    await redis.hset(token, { u: 'www.test.com', p: 1 });
    await redis.del('c:' + token);

    const response = await request(app).post('/').send({ url });

    expect(response.status).toBe(200);
    expect(response.body).toEqual({
      sUrl: expect.any(String),
      expireIn: expect.any(Number),
    });
    expect(response.body.sUrl).not.toBe(token);
    expect(response.body.sUrl.substr(-token.length)).toBe(token);
  });

  it('请求不存在的短网址，已发生10次冲突，为其扩展为其他的token', async () => {
    const url = 'https://www.baidu.com';
    const token = genSUrlToken('https://www.baidu.com');
    await redis.hset(token, { u: 'www.test.com', p: 1 });
    await redis.hset(token + '1', { u: 'www.test.com', p: 1 });
    await redis.set('c:' + token, 10);

    const response = await request(app).post('/').send({ url });

    expect(response.status).toBe(200);
    expect(response.body).toEqual({
      sUrl: expect.any(String),
      expireIn: expect.any(Number),
    });
    expect(response.body.sUrl).not.toBe(token);
    expect(response.body.sUrl.substr(-token.length)).toBe(token);
  });

  it('请求不存在的短网址，已发生100000次剧烈冲突，为其扩展为其他的token', async () => {
    const url = 'https://www.baidu.com';
    const token = genSUrlToken('https://www.baidu.com');
    await redis.hset(token, { u: 'www.test.com', p: 1 });
    await redis.hset(token + '1', { u: 'www.test.com', p: 1 });
    await redis.set('c:' + token, 100000);

    const response = await request(app).post('/').send({ url });

    expect(response.status).toBe(200);
    expect(response.body).toEqual({
      sUrl: expect.any(String),
      expireIn: expect.any(Number),
    });
    expect(response.body.sUrl).not.toBe(token);
    expect(response.body.sUrl.substr(-token.length)).not.toBe(token);
  });

  it('请求不存在的短网址，已发生100000次剧烈冲突，为其扩展为其他的token时也超过阈值，总计10次后失败', async () => {
    const url = 'https://www.baidu.com';
    const token = genSUrlToken('https://www.baidu.com');
    await redis.hset(token, { u: 'www.test.com', p: 1 });
    await redis.hset(token + '1', { u: 'www.test.com', p: 1 });
    await redis.set('c:' + token, 100000);
    for (let i = 10; i > 0; i--) {
      const next = genSUrlToken(token + '|' + i + '|' + url);
      await redis.set('c:' + next, 100000);
      await redis.hset(next, { u: 'www.test.com', p: 1 });
    }

    const response = await request(app).post('/').send({ url });

    expect(response.status).toBe(500);
  });
});

describe('集成测试', () => {
  const { app, redis } = initTestApp({});
  jest.setTimeout(60000);
  const testProcess = async (url: string) => {
    const response = await request(app).post('/').send({ url });

    expect(response.status).toBe(200);
    expect(response.body).toEqual({
      sUrl: expect.any(String),
      expireIn: expect.any(Number),
    });

    const response2 = await request(app).get(`/${response.body.sUrl}`);

    expect(response2.status).toBe(302);
    expect(response2.headers.location).toBe(url);
  };

  it('创建了一个短网址，然后尝试使用短网址进行跳转', async () => {
    await testProcess('https://www.google.com');
  });

  it('创建若干短网址，然后逐个尝试跳转', async () => {
    const urls = [
      'https://www.google.com/',
      'https://www.youtube.com/',
      'https://www.facebook.com/',
      'https://www.amazon.com/',
      'https://www.wikipedia.org/',
      'https://www.twitter.com/',
      'https://www.instagram.com/',
      'https://www.linkedin.com/',
      'https://www.reddit.com/',
      'https://www.quora.com/',
      'https://www.github.com/',
      'https://www.stackoverflow.com/',
      'https://www.medium.com/',
      'https://www.netflix.com/',
      'https://www.spotify.com/',
      'https://www.apple.com/',
      'https://www.microsoft.com/',
      'https://www.ibm.com/',
      'https://www.salesforce.com/',
      'https://www.paypal.com/',
      'https://www.baidu.com/',
      'https://www.taobao.com/',
      'https://www.jd.com/',
      'https://www.tencent.com/',
      'https://www.alibaba.com/',
      'https://www.weibo.com/',
      'https://www.zhihu.com/',
      'https://www.douban.com/',
      'https://www.toutiao.com/',
      'https://www.meituan.com/',
      'https://www.ctrip.com/',
      'https://www.xiaomi.com/',
      'https://www.huawei.com/',
      'https://www.bytedance.com/',
      'https://www.qq.com/',
      'https://www.163.com/',
      'https://www.sohu.com/',
      'https://www.ifeng.com/',
      'https://www.58.com/',
      'https://www.pinduoduo.com/',
    ];

    for (const url of urls) {
      await testProcess(url);
    }
  });

  it('请求各种随机生成的网址，请求1000次', async () => {
    const domains = [
      //常规域名后缀
      'com',
      'net',
      'org',
      'gov',
      'edu',
      'biz',
    ];
    let times = 1000;
    while (times-- > 0) {
      const domain = domains[Math.floor(Math.random() * domains.length)];
      const url = `https://www.${Math.random().toString(36)}.${domain}`;
      await testProcess(url);
    }
    // const keys = await redis.keys('c:*');
    // expect(keys.length).toBe(0);
  });
});
