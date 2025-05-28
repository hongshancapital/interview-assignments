import 'mocha';
import chai, { expect } from 'chai';
import chaiHttp from 'chai-http';
import app from '../../app';
import { getBytes } from '../../server/utils/string';

chai.use(chaiHttp);

const loongUrl = "https://www.taobao.com/asdfafsdafdasfdsf?asdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfasdfafsdafdasfdsfxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgxqqusfadooopjgqqusfadqqusfadqqusfadqqusfadqqusfadqqusfadqqusfadqqusfadqqusfadqqusfadqqusfadqqusfadqqusfadqqusfaddqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqqdqqusfadqqusfadqq";
const promiseHttp = (url: string, params: any) => new Promise((resolve, reject) => {
    chai.request(app)
    .post(url)
    .send(params)
    .end((err, res: any) => {
      resolve(res.body);
    });
  });

describe('Api generateShortLink', () => {
  it('should not support get', (done) => {
    chai.request(app)
      .get('/api/generateShortLink')
      .end((err, res: any) => {
        expect(res.statusCode).to.be.equal(404);
        expect(err).to.be.equal(null);
        done();
      });
  });

  it('should support post', (done) => {
    chai.request(app)
      .post('/api/generateShortLink')
      .send({
        link: 'https://www.baidu.com'
      })
      .end((err, res: any) => {
        expect(res.statusCode).to.be.equal(200);
        expect(err).to.be.equal(null);
        done();
      });
  });

  it('params link required(code 400)', (done) => {
    chai.request(app)
      .post('/api/generateShortLink')
      .send({
        a: 'abc'
      })
      .end((err, res: any) => {
        const { message, code } = res.body;
        expect(code).to.be.equal(400);
        expect(message).to.be.equal("Params(link) should be a string!");
        expect(err).to.be.equal(null);
        done();
      });
  });

  it('params link should not be empty(code 400)', (done) => {
    chai.request(app)
      .post('/api/generateShortLink')
      .send({
        link: ''
      })
      .end((err, res: any) => {
        const { message, code } = res.body;
        expect(code).to.be.equal(400);
        expect(message).to.be.equal("Params(link) can not be empty!");
        expect(err).to.be.equal(null);
        done();
      });
  });

  it('params link should be string(code 400)', (done) => {
    chai.request(app)
      .post('/api/generateShortLink')
      .send({
        link: {
          porperty: '1'
        }
      })
      .end((err, res: any) => {
        const { message, code } = res.body;
        expect(code).to.be.equal(400);
        expect(message).to.be.equal("Params(link) should be a string!");
        expect(err).to.be.equal(null);
        done();
      });
  });

  it('params link should be valid url(code 400)', (done) => {
    chai.request(app)
      .post('/api/generateShortLink')
      .send({
        link: 'http:abcdefg?abc'
      })
      .end((err, res: any) => {
        const { message, code } = res.body;
        expect(code).to.be.equal(400);
        expect(message).to.be.equal("Params(link) not valid! Please check your input.");
        expect(err).to.be.equal(null);
        done();
      });
  });

  it('params link should be over 2118 bytes(code 414)', (done) => {
    console.log('params bytes is =>', getBytes(loongUrl));
    chai.request(app)
      .post('/api/generateShortLink')
      .send({
        link: loongUrl
      })
      .end((err, res: any) => {
        const { message, code } = res.body;
        expect(code).to.be.equal(414);
        expect(message).to.be.equal("Params link is too long! The limit bytes is 2118.");
        expect(err).to.be.equal(null);
        done();
      });
  });

  it('params link should not support wx(code 400)', (done) => {
    chai.request(app)
      .post('/api/generateShortLink')
      .send({
        link: 'ws://localhost:3000'
      })
      .end((err, res: any) => {
        const { message, code } = res.body;
        expect(code).to.be.equal(400);
        expect(message).to.be.equal("Params(link) not valid! Please check your input.");
        expect(err).to.be.equal(null);
        done();
      });
  });

  it('params link should support http(code 200)', (done) => {
    chai.request(app)
      .post('/api/generateShortLink')
      .send({
        link: 'http://www.baidu.com'
      })
      .end((err, res: any) => {
        const { message, code } = res.body;
        expect(code).to.be.equal(0);
        expect(message).to.be.equal("success");
        expect(err).to.be.equal(null);
        done();
      });
  });

  it('params link should support https(code 200)', (done) => {
    chai.request(app)
      .post('/api/generateShortLink')
      .send({
        link: 'https://www.taobao.com'
      })
      .end((err, res: any) => {
        const { message, code } = res.body;
        expect(code).to.be.equal(0);
        expect(message).to.be.equal("success");
        expect(err).to.be.equal(null);
        done();
      });
  });

  it('params link should support ftp(code 200)', (done) => {
    chai.request(app)
      .post('/api/generateShortLink')
      .send({
        link: 'ftp://www.taobao.com?a=2'
      })
      .end((err, res: any) => {
        const { message, code } = res.body;
        expect(code).to.be.equal(0);
        expect(message).to.be.equal("success");
        expect(err).to.be.equal(null);
        done();
      });
  });

  it('same params should return different shortlink(code 200)', (done) => {
    const url = '/api/generateShortLink';
    const params = {
      link: 'https://www.taobao.com?item=123'
    };
    Promise.all([
      promiseHttp(url, params),
      promiseHttp(url, params)
    ]).then((res: any) => {
      const [data1, data2] = res;
      expect(data1.code).to.be.equal(0);
      expect(data1.message).to.be.equal("success");
      expect(data2.code).to.be.equal(0);
      expect(data2.message).to.be.equal("success");

      expect(data1.data.origin === data2.data.origin).to.be.equal(true);
      expect(data1.data.short === data2.data.short).to.be.equal(false);
      done();
    });
  });

  it('some requests in the same time(code 200)', (done) => {
    const url = '/api/generateShortLink';
    const params = {
      link: 'https://www.taobao.com?item=123'
    };
    Promise.all([
      promiseHttp(url, params),
      promiseHttp(url, params),
      promiseHttp(url, params),
      promiseHttp(url, params),
      promiseHttp(url, params),
      promiseHttp(url, params),
      promiseHttp(url, params),
      promiseHttp(url, params),
      promiseHttp(url, params),
      promiseHttp(url, params)
    ]).then((res: any) => {
      let allSuccess = true;
      res.forEach((item: { code: number; }) => {
        if (item.code) allSuccess = false;
      });

      expect(allSuccess).to.be.equal(true);
      done();
    });
  });
})
