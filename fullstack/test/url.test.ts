import request from 'supertest';
import app from './../app';
import {describe,it,expect} from '@jest/globals';
describe('creat url test', () => {
  it('creat with no url', async () => {
    //@ts-ignore
    const response = await request(app).post('/surl/')
       .set('Accept', 'application/json')
       .expect('Content-Type', "application/json; charset=utf-8");
      expect(response.body.msg).toEqual('url参数不合法！');
  });
  it('creat with wrong url', async () => {
    //@ts-ignore
    const response = await request(app).post('/surl/')
       .set('Accept', 'application/json')
       .send({"url": "123343lddd//"})
       .expect('Content-Type', "application/json; charset=utf-8");
      expect(response.body.msg).toEqual('url参数不合法！');
  });
  it('creat with have url', async () => {
    //@ts-ignore
    const response = await request(app).post('/surl/')
       .set('Accept', 'application/json')
       .send({"url": "http://www.51yuansu.com/sc/vsvhhktgav18.html"})
       .expect('Content-Type', "application/json; charset=utf-8");
      expect(response.body.msg).toEqual('添加成功！');
  });
  it('creat repeat', async () => {
    //@ts-ignore
    const response = await request(app).post('/surl/')
       .set('Accept', 'application/json')
       .send({"url": "http://www.51yuansu.com/sc/vsvhhktgav.html"})
       .expect('Content-Type', "application/json; charset=utf-8");
      expect(response.body.msg).toEqual("链接已存在");
  });
});

describe('get url test', () => {
  it('get  no url', async () => {
    //@ts-ignore
    const response = await request(app).get('/surl/')
       .set('Accept', 'application/json');
      expect(response.body.msg).toEqual(undefined);
  });
  it('get right url', async () => {
    //@ts-ignore
    const response = await request(app).get('/surl/12345678')
       .set('Accept', 'application/json').
       expect(200);
  });
  it('get wrong url', async () => {
    //@ts-ignore
    const response = await request(app).get('/surl/12345679')
       .set('Accept', 'application/json');
      expect(response.body.msg).toEqual('不存在！');
  });
});

