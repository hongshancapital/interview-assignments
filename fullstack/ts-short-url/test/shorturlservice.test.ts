
import supertest from 'supertest'
import express from 'express'
import http from 'http'
import { ShortUrlService } from "../src/service/ShortUrlService"
import {Server} from "../src/server"

let server_app: express.Application;
let httpServer: http.Server;

const initServer = () => {
  server_app = Server.bootstrap().app;
  httpServer = http.createServer(server_app);
  httpServer.listen(8080);
}

const UnitServer = () =>{
  httpServer.close();
}

beforeAll(async () => {
  await initServer()
});

afterAll(async () => {
  UnitServer();
});

const createShortUrlApi = (originalUrl?: string) =>
  supertest(server_app)
    .post('/shortUrl')
    .set('Content-Type', 'application/json')
    .send({ originalUrl });

const createOriginalUrlApi = (shortUrl?: string) =>
  supertest(server_app)
    .post('/originalUrl')
    .set('Content-Type', 'application/json')
    .send({ shortUrl });
  


describe('shorturl create', () => {  
  test('base create url', async () => {
    const res = await createShortUrlApi("https://www.baidu.com/rsv_t=b962RRXTbtksyRjfNOH1gNTeks%2BDGAAB64avxqan3wj8C0%2F6bjFrRdIDVFlUjVnp14k7");
    expect(res.statusCode).toEqual(200)
    expect(res.body.shortUrl).toEqual("http://s.cn/ZjA6Ebaa")
  })
})



describe('shorturl create', () => {  
  test('base create url', async () => {
    const res = await createOriginalUrlApi("http://s.cn/ZjA6Ebaa");
    expect(res.statusCode).toEqual(200)
    expect(res.body.originalUrl).toEqual("https://www.baidu.com/rsv_t=b962RRXTbtksyRjfNOH1gNTeks%2BDGAAB64avxqan3wj8C0%2F6bjFrRdIDVFlUjVnp14k7")
  })
})


/*
let urlServer:ShortUrlService = new ShortUrlService();

describe('queryOrGenerateShortUrl Server', () => {
  it('should be able to add things correctly' , () => {
    expect(urlServer.queryOrGenerateShortUrl("https://www.baidu.com/rsv_t=b962RRXTbtksyRjfNOH1gNTeks%2BDGAAB64avxqan3wj8C0%2F6bjFrRdIDVFlUjVnp14k7")).toBe("http://s.cn/3p8YIbaa");
  });
});

describe('queryOriginalUrl Server', () => {
  it('should be able to add things correctly' , () => {
    expect(urlServer.queryOriginalUrl("http://s.cn/3p8YIbaa")).toBe("https://www.baidu.com/rsv_t=b962RRXTbtksyRjfNOH1gNTeks%2BDGAAB64avxqan3wj8C0%2F6bjFrRdIDVFlUjVnp14k7");
  });
});

*/