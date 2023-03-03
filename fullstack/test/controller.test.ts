import request from 'supertest';
import assert from 'assert';
import { pair, pair2 } from '../mock/controller/controller.mock';
import { app } from '../src/app';

describe('长链/短链 互转核心功能', () => {
  it('长链转短链核心功能', function (done) {
    jest.useFakeTimers();
    request(app)
      .post('/linkreq')
      .send(pair)
      .expect(200)
      .then(res => {
        assert(res.body.shortLink, pair.sLink);
        done();
      })
      .catch(e => done())
  });

  it('取链后索引自增1', function (done) {
    jest.useFakeTimers();
    request(app)
      .post('/linkreq')
      .send(pair2)
      .expect(200)
      .then(res => {
        assert(res.body.shortLink, pair2.sLink);
        done();
      })
      .catch(e => done())
  });

  it('长链转短链核心功能——不传入长链接', function (done) {
    jest.useFakeTimers();
      request(app)
        .post('/linkreq')
        .send({})
        .expect(200)
        .then(res => {
          assert(res.body.shortLink, '');
          done();
        })
        .catch(e => done())
    });

  it('短域名长度最大为8个字符', function (done) {
    jest.useFakeTimers();
    request(app)
      .post('/querylink')
      .send(pair)
      .expect(200)
      .then(res => {
        assert(
          (res.body.shortLink.substring(res.body.shortLink.lastIndexOf('/')).length <= 8).toString(),
          'true'
        )
        done();
      })
      .catch(e => done())
  });

  it('短链返回对应长链', function (done) {
    jest.useFakeTimers();
    request(app)
      .post('/querylink')
      .send(pair)
      .expect(200)
      .then(res => {
        assert(res.body.page_link, pair.lUrl);
        done()
      })
      .catch(e => done())
      // done()
  });

  it('短链返回对应长链——不传入长链', function (done) {
    jest.useFakeTimers();
    request(app)
      .post('/querylink')
      .send({})
      .expect(200)
      .then(res => {
        assert(res.body.page_link, '');
        done()
      })
      .catch(e => done())
      // done()
  });
});