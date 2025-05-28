import 'mocha';
import chai, { expect } from 'chai';
import chaiHttp from 'chai-http';
import app from '../../app';

chai.use(chaiHttp);

const promisePost = (url: string, params: any) => new Promise((resolve, reject) => {
    chai.request(app)
    .post(url)
    .send(params)
    .end((err, res: any) => {
      resolve(res.body);
    });
  });

const promiseGet = (url: string) => new Promise((resolve, reject) => {
  chai.request(app)
  .get(url)
  .end((err, res: any) => {
    resolve(res.body);
  });
});

describe('Api getOriginLink', () => {
  // it('should support get', (done) => {
  //   chai.request(app)
  //     .get('/api/getOriginLink')
  //     .end((err, res: any) => {
  //       expect(res.statusCode).to.be.equal(200);
  //       expect(err).to.be.equal(null);
  //       done();
  //     });
  // });

  // it('should not support post', (done) => {
  //   chai.request(app)
  //     .post('/api/getOriginLink')
  //     .send({
  //       link: 'https://www.baidu.com'
  //     })
  //     .end((err, res: any) => {
  //       expect(res.statusCode).to.be.equal(404);
  //       expect(err).to.be.equal(null);
  //       done();
  //     });
  // });

  // it('[property]query key required(code 400)', (done) => {
  //   chai.request(app)
  //     .get('/api/getOriginLink')
  //     .end((err, res: any) => {
  //       const { message, code } = res.body;
  //       expect(code).to.be.equal(400);
  //       expect(message).to.be.equal("Params(key) can not be empty!");
  //       expect(err).to.be.equal(null);
  //       done();
  //     });
  // });

  // it('[value]query key required(code 400)', (done) => {
  //   chai.request(app)
  //     .get('/api/getOriginLink?key=')
  //     .end((err, res: any) => {
  //       const { message, code } = res.body;
  //       expect(code).to.be.equal(400);
  //       expect(message).to.be.equal("Params(key) can not be empty!");
  //       expect(err).to.be.equal(null);
  //       done();
  //     });
  // });

  // it('[unsported character]query key should be valid(/^[a-zA-Z0-9]{1,8}$/)(code 400)', (done) => {
  //   chai.request(app)
  //     .get('/api/getOriginLink?key=#*&')
  //     .end((err, res: any) => {
  //       console.log(res.body);
  //       const { message, code } = res.body;
  //       expect(code).to.be.equal(400);
  //       expect(message).to.be.equal("Params(key) can not be empty!");
  //       expect(err).to.be.equal(null);
  //       done();
  //     });
  // });

  // it('[too loong]query key should be valid(/^[a-zA-Z0-9]{1,8}$/)(code 400)', (done) => {
  //   chai.request(app)
  //     .get('/api/getOriginLink?key=abcdefghijk')
  //     .end((err, res: any) => {
  //       const { message, code } = res.body;
  //       expect(code).to.be.equal(400);
  //       expect(message).to.be.equal("Short link path not valid. Please check your input!");
  //       expect(err).to.be.equal(null);
  //       done();
  //     });
  // });

  // it('get the right insert origin link(code 200)', (done) => {
  //   const url = '/api/generateShortLink';
  //   const link1 = 'https://www.taobao.com?item=123';
  //   const link2 = 'https://www.taobao.com?item=234';
  //   const link3 = 'https://www.taobao.com?item=345';
  //   Promise.all([
  //     promisePost(url, {
  //       link: link1
  //     }),
  //     promisePost(url, {
  //       link: link2
  //     }),
  //     promisePost(url, {
  //       link: link3
  //     })
  //   ]).then((result: any) => {
  //     const [ data1, data2, data3 ] = result;

  //     expect(data1.data.origin).to.be.equal(link1);
  //     expect(data2.data.origin).to.be.equal(link2);
  //     expect(data3.data.origin).to.be.equal(link3);

  //     const api = '/api/getOriginLink';
  //     Promise.all([
  //       promiseGet(`${api}?key=${data1.data.short}`),
  //       promiseGet(`${api}?key=${data2.data.short}`),
  //       promiseGet(`${api}?key=${data3.data.short}`),
  //     ]).then((response: any) => {
  //       const [ res1, res2, res3 ] = response;
  //       expect(res1.data).to.be.equal(link1);
  //       expect(res2.data).to.be.equal(link2);
  //       expect(res3.data).to.be.equal(link3);
  //       done();
  //     });
  //   });
  // });
})
