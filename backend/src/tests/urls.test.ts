import request from 'supertest';
import App from '@/app';
import { ShortUrl } from '@interfaces/urls.interface';
import ShortUrlModel from '@models/urls.model';
import UrlRoute from '@routes/urls.route';

afterAll(async () => {
  await new Promise<void>(resolve => setTimeout(() => resolve(), 500));
});

describe('Testing Url', () => {
  describe('[GET] /url', () => {
    it('response statusCode 200 / findAllUrls', () => {
      const resData: ShortUrl[] = ShortUrlModel;
      const urlRoute = new UrlRoute();
      const app = new App([urlRoute]);

      return request(app.getServer()).get(`${urlRoute.path}`).expect(200, { data: resData, message: 'ok' });
    });
  });

  describe('[GET] /url/:url', () => {
    it('response statusCode 200 / getByShortUrl', () => {
      const url = '12345678';
      const resData: ShortUrl = ShortUrlModel.find(item => item.short === url);
      const urlRoute = new UrlRoute();
      const app = new App([urlRoute]);

      return request(app.getServer()).get(`${urlRoute.path}/${url}`).expect(200, { data: resData.long, message: 'ok' });
    });
  });

  describe('[GET] /url/:url', () => {
    it('response statusCode 200 / getByShortUrl', () => {
      const url = '111';
      const urlRoute = new UrlRoute();
      const app = new App([urlRoute]);

      return request(app.getServer()).get(`${urlRoute.path}/${url}`).expect(400, { message: 'Url params invalid' });
    });
  });

  describe('[POST] /url', () => {
    it('response statusCode 201 / createWithLongUrl', async () => {
      const postData = { url: 'http://baidu.com/index.html' };
      const urlRoute = new UrlRoute();
      const app = new App([urlRoute]);

      return request(app.getServer()).post(`${urlRoute.path}`).send(postData).expect(201);
    });
  });

});
