import { HttpStatus, ErrorCode } from '../../common/interface/errorCode.interface';
import supertest from 'supertest';
import { app } from '../../../src/app';
import { config } from '../../common/config/index' 
describe('Router', () => {
  let request: supertest.SuperTest<supertest.Test>;
  beforeEach(() => {
    request = supertest(app);
  });

  describe('POST /shortUrl', () => {
    it('should return 400 if longUrl is empty', async () => {
      const res = await request
                    .post('/shortUrl')
                    .set('Content-Type', 'application/json; charset=UTF-8')
                    .send({
                      longUrl:''
                    })
      expect(res.status).toEqual(HttpStatus.BAD_REQUEST);
      expect(res.body.code).toEqual(ErrorCode.ParamError);
    });

    it('should return 400 if longUrl is longer than 4098', async () => {
      const res = await request
                    .post('/shortUrl')
                    .set('Content-Type', 'application/json; charset=UTF-8')
                    .send({
                      longUrl:`http://${new Array(5000).fill('a').join('')}.com`
                    })
      expect(res.status).toEqual(HttpStatus.BAD_REQUEST);
      expect(res.body.code).toEqual(ErrorCode.ParamError);

    });

    it('should return 200 if longUrl is valid', async () => {
      const baseUrlLength = config.baseUrl.length;
      const res = await request
                    .post('/shortUrl')
                    .set('Content-Type', 'application/json; charset=UTF-8')
                    .send({
                      longUrl:'http://www.test.com'
                    })
      expect(res.status).toEqual(HttpStatus.OK);
      expect(res.body.code).toEqual(ErrorCode.Sucess);
      expect(res.body.data.shortUrl).toMatch(/^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/);
      expect(res.body.data.shortUrl.length).toBeLessThan(9 + baseUrlLength);
    });
  });

  describe('GET /longUrl', () => {
    it('should return 400 if shortUrl is empty', async () => {
      const res = await request
                    .get('/longUrl')
                    .set('Content-Type', 'application/json; charset=UTF-8')
                    .query({
                      shortUrl:''
                    })
      expect(res.status).toEqual(HttpStatus.BAD_REQUEST);
      expect(res.body.code).toEqual(ErrorCode.ParamError);
    });

    it('should return 400 if shortUrl is longer than 8', async () => {
      const res = await request
                    .get('/longUrl')
                    .set('Content-Type', 'application/json; charset=UTF-8')
                    .query({
                      shortUrl:'ZSObExHfafa3'
                    })
      expect(res.status).toEqual(HttpStatus.BAD_REQUEST);
      expect(res.body.code).toEqual(ErrorCode.ParamError);
    });

    it('should return 200 if shortUrl is valid', async () => {
      const res = await request
                    .get('/longUrl')
                    .set('Content-Type', 'application/json; charset=UTF-8')
                    .query({
                      shortUrl:'ZSObExH3'
                    })
      expect(res.status).toEqual(HttpStatus.OK);
      expect(res.body.code).toEqual(ErrorCode.Sucess);
      expect(res.body.data.longUrl).toMatch(/^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/);
      expect(res.body.data.longUrl.length).toBeLessThan(4097);
    });
  });
});