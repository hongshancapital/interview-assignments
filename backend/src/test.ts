import mongoose from "mongoose";
import superTest from "supertest";
import { httpServer } from ".";

const longUrl = 'www.baidu.com';
let _id = null;
const longUrl2 = 'www.baidu2.com';
let _id2 = null;

test('Get short URL', (done) => {
  superTest(httpServer)
    .post('/url')
    .send({ longUrl })
    .end((err, res) => {
      const text = res.text;
      const json = JSON.parse(text);
      expect(json.message).toBe('success');
      _id = json.data._id;
      done();
    });
});

test('Get same short URL', (done) => {
  expect(_id).not.toBeNull();
  superTest(httpServer)
    .post('/url')
    .send({ longUrl })
    .end((err, res) => {
      const text = res.text;
      const json = JSON.parse(text);
      expect(json.message).toBe('success');
      expect(json.data._id).toBe(_id);
      done();
    });
});

test('Get different short URL', (done) => {
  expect(_id).not.toBeNull();
  superTest(httpServer)
    .post('/url')
    .send({ longUrl: longUrl2 })
    .end((err, res) => {
      const text = res.text;
      const json = JSON.parse(text);
      expect(json.message).toBe('success');
      expect(json.data._id).not.toBe(_id);
      _id2 = json.data._id;
      done();
    });
});

test('Get long URL', (done) => {
  expect(_id).not.toBeNull();
  superTest(httpServer)
    .get(`/${_id}`)
    .end((err, res) => {
      const text = res.text;
      const json = JSON.parse(text);
      expect(json.message).toBe('success');
      expect(longUrl).toBe(json.data.longUrl);
      done();
    });
});

test('Get long URL but short URL no found', (done) => {
  expect(_id).not.toBeNull();
  superTest(httpServer)
    .get(`/${Math.random()}`)
    .end((err, res) => {
      const text = res.text;
      const json = JSON.parse(text);
      expect(json.message).toBe('url 404');
      done();
    });
});

test('Get different long URL', (done) => {
  expect(_id).not.toBeNull();
  superTest(httpServer)
    .get(`/${_id2}`)
    .end((err, res) => {
      const text = res.text;
      const json = JSON.parse(text);
      expect(json.message).toBe('success');
      expect(longUrl).not.toBe(json.data.longUrl);
      expect(longUrl2).toBe(json.data.longUrl);
      done();
    });
});

test('Get long URL but do not pass a value', (done) => {
  expect(_id).not.toBeNull();
  superTest(httpServer)
    .post("/url")
    .send({})
    .end((err, res) => {
      const text = res.text;
      const json = JSON.parse(text);
      expect(json.message).toBe('invalid url');
      done();
    });
});

afterAll(() => {
  httpServer.close();
  mongoose.disconnect();
});