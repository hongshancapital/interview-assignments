import mock, { MockApplication } from 'egg-mock';
import { assert } from 'egg-mock/bootstrap';
import { BLOOM_REDIS_KEY, URL_PTEFIX } from '../../app/utils/constants';

function errorMessage(message: string) {
  return {
    success: false,
    error: {
      code: 400,
      message,
    },
  };
}

describe('test/app/controller/url-map.test.ts', () => {
  let app: MockApplication;

  before(() => {
    // 创建当前应用的 app 实例
    app = mock.app();
    // 等待 app 启动成功，才能执行测试用例
    return app.ready();
  });

  afterEach(mock.restore);

  describe('url-map-controller', () => {
    it('redirect', async () => {
      const TINY_URL = {
        correct: 'ScNiN2Rg',
        notExist: '00000000',
        incorrectNumber: 'quws',
        incorrectCharacter: 'quws_^12',
      };
      await app.httpRequest().get(`/${TINY_URL.correct}`)
        .expect(302);

      await app.httpRequest().get(`/${TINY_URL.notExist}`)
        .expect(200)
        .expect(errorMessage('短链无效'));

      await app.httpRequest().get(`/${TINY_URL.incorrectNumber}`)
        .expect(200)
        .expect(errorMessage('tinyUrl 参数错误'));

      await app.httpRequest().get(`/${TINY_URL.incorrectCharacter}`)
        .expect(200)
        .expect(errorMessage('tinyUrl 参数错误'));
    });

    it('set error originalUrl', async () => {
      const originalUrl = 'https://juejin.cn/post/7065585389574029320';

      await app.httpRequest().post('/api/tinyUrl')
        .send({ creator: 'weiruifeng' })
        .expect(200)
        .expect(errorMessage('originalUrl 参数错误'));

      await app.httpRequest().post('/api/tinyUrl')
        .send({ originalUrl })
        .expect(200)
        .expect(errorMessage('creator 参数错误'));
    });

    it('The test generates tinyUrl and exchanges originalUrl according to tinyUrl', async () => {
      const originalUrl = 'https://github.com/weiruifeng';
      const setInfoRes = await app.httpRequest().post('/api/tinyUrl')
        .send({
          originalUrl,
          creator: 'weiruifeng',
        });

      let tinyUrl = setInfoRes.body.data.tinyUrl;
      // 域名是否正确
      assert(tinyUrl.indexOf(URL_PTEFIX) === 0);
      const regexp = /^[0-9a-zA-Z]{8}$/;
      tinyUrl = tinyUrl.substr(URL_PTEFIX.length);
      // 短链接是否符合正则
      assert(regexp.test(tinyUrl));

      const originalUrlRes = await app.httpRequest().get(`/api/tinyUrl?tinyUrl=${tinyUrl}`);
      // 获取原始链接与生成的链接是否一致
      assert(originalUrlRes.body.data.originalUrl === originalUrl);
      const delRes = await app.httpRequest().delete('/api/tinyUrl')
        .send({ tinyUrl })
        .expect(200);
      assert(delRes.body.data.count === 1);
      // 删除 bloom 过滤器
      await app.redisClient.del(BLOOM_REDIS_KEY);
    });
  });
});

