import { Sequelize } from 'sequelize';
import request from 'supertest';
import App from '@/app';
import { CreateShortDto } from '@dtos/shorts.dto';
import ShortsRoute from '@routes/shorts.route';

afterAll(async () => {
  await new Promise<void>(resolve => setTimeout(() => resolve(), 500));
});

describe('Testing Shorts', () => {

  describe('[GET] /shorts/:shortKey', () => {
    it('response findOne short', async () => {
      const shortKey = 'QTrcy6Eq';

      const shortsRoute = new ShortsRoute();
      const shorts = shortsRoute.shortsController.shortService.shorts;

      shorts.findOne = jest.fn().mockReturnValue({
        id: 1,
        origin: 'https://github.com/mikolalysenko/murmurhash-js/blob/master/murmurhash3_gc.js',
        shortKey: 'QTrcy6Eq',
        md5: '44223f0757df5a3700062a33be5ebb09',
        get(){
          return this;
        }
      });

      (Sequelize as any).authenticate = jest.fn();
      const app = new App([shortsRoute]);
      return request(app.getServer()).get(`${shortsRoute.path}/${shortKey}`).expect(200);
    });
  });

  describe('[POST] /shorts', () => {
    it('response Create short', async () => {
      const shortData: CreateShortDto = {
        origin: 'https://www.expressjs.com.cn/guide/routing.html'
      };

      const shortsRoute = new ShortsRoute();
      const shorts = shortsRoute.shortsController.shortService.shorts;

      shorts.findOne = jest.fn().mockReturnValue(null);
      shorts.create = jest.fn().mockReturnValue({
        id: 2,
        origin: shortData.origin,
        md5: '1804ec7b3b17c466235b92d6cc281c42',
        shortKey: '2kLUFHSH',
        updatedAt: '2023-03-04T07:08:34.159Z',
        createdAt: '2023-03-04T07:08:34.159Z'
      });

      (Sequelize as any).authenticate = jest.fn();
      const app = new App([shortsRoute]);
      return request(app.getServer()).post(`${shortsRoute.path}`).send(shortData).expect(201);
    });
  });

});
