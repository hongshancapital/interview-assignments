import mock, { MockApplication } from 'egg-mock';

describe('test/app/schedule/url-map.test.ts', () => {
  let app: MockApplication;

  before(() => {
    // 创建当前应用的 app 实例
    app = mock.app();
    // 等待 app 启动成功，才能执行测试用例
    return app.ready();
  });

  afterEach(mock.restore);

  describe('url-map-schedule', () => {
    it('run schedule', async () => {
      await app.runSchedule('url-map');
    });
  });
});

