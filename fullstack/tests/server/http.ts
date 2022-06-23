import { describe, it } from "mocha";
import assert from "assert";
import axios from 'axios'
import { ShortUrl } from '../../src/models/shortUrl';
import { ResponseData, StatusCode } from '../../src/models/http';
import { createShortUrl } from '../../src/client/requests';

describe("Http", function () {
  describe("Test class ResponseData", function () {
    it("instance", async function () {
      const instance1 = new ResponseData<number>('test message', 1);
      assert.equal(instance1.message, 'test message');
      assert.equal(instance1.payload, 1);

      const instance2 = new ResponseData<boolean>('test message');
      assert.equal(instance2.message, 'test message');
      assert.equal(instance2.payload, null);
    });
  });

  describe("Test client request", function () {
    it("Should create shorter", async function () {
      const url = `http://localhost:3000/api/shorturls`;
      const res1 = await createShortUrl(url)
      const res2 = await axios.get<ResponseData<ShortUrl>>(
        `${url}/${res1.short_path}`,
      )
      assert.equal(res1.short_path, res2.data.payload?.short_path);
    });
  });

  describe("Test server routers", function () {
    it("Should create shorter", async function () {
      const url = `http://localhost:3000/api/shorturls`;
      const res1 = await axios.post<ResponseData<ShortUrl>>(
        `${url}`,
        { long_url: url },
        { headers: { 'content-type': 'application/json' } },
      )
      const res2 = await axios.get<ResponseData<ShortUrl>>(
        `${url}/${res1.data.payload?.short_path}`,
      )
      assert.equal(res1.data.payload?.short_path, res2.data.payload?.short_path);
    });

    it("Should redirect", async function () {
      const url = `http://www.baidu.com`;
      const res1 = await axios.post<ResponseData<ShortUrl>>(
        `${url}`,
        { long_url: url },
        { headers: { 'content-type': 'application/json' } },
      )
      const res2 = await axios.get<ResponseData<ShortUrl>>(
        `${url}/s/${res1.data.payload?.short_path}`,
      )
      console.log(res2);
      assert.equal(res2.status, StatusCode.Redirect);
    });
  });
});
