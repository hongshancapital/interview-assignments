
import supertest from 'supertest'
import express from 'express'
import http from 'http'
//import { ShortUrlService } from "../src/service/ShortUrlService"
import {Server} from "../src/server"
import {ShortUrlGenerator} from "../src/utils/shorturlgenerator"

let server_app: express.Application;
let httpServer: http.Server;

// 启动服务
const initServer = async () => {

  await Server.InitServer();

  server_app = Server.bootstrap().app;
  httpServer = http.createServer(server_app);
  httpServer.listen(8080);
}

// 停止服务
const UnitServer = async () =>{
  await Server.UnitServer();
  httpServer.close();
}

// 单元测试初始化
beforeAll(async () => {
  await initServer()
});

// 单元测试结束
afterAll(async () => {
  await UnitServer();
});

// 测试shortUrl接口的正确性
const createShortUrlApi = (originalUrl?: string) =>
  supertest(server_app)
    .post('/shortUrl')
    .set('Content-Type', 'application/json')
    .send({ originalUrl });

describe('shortUrl接口测试', () => {  
  test('shortUrl接口的正确性', async () => {
    const res = await createShortUrlApi("https://www.baidu.com/rsv_t=b962R1RXTbtksyRjfNOH1gNTeks%2BDGAAB64avxqan3wj8C0%2F6bjFrRdIDVFlUjVnp14k71");
    expect(res.statusCode).toEqual(200)
    expect(res.body.code).toEqual("SU_OK")
  })
})

describe('shortUrl接口测试', () => {  
  test('shortUrl重复申请short URL测试', async () => {
    const res = await createShortUrlApi("https://www.baidu.com/rsv_t=b962R1RXTbtksyRjfNOH1gNTeks%2BDGAAB64avxqan3wj8C0%2F6bjFrRdIDVFlUjVnp14k71");
    expect(res.statusCode).toEqual(200)
    expect(res.body.code).toEqual("SU_OK")
  })
})

// 异常shortUrl接口测试-参数异常
const createShortUrlErroeApi = (unknow?: string) =>
  supertest(server_app)
    .post('/shortUrl')
    .set('Content-Type', 'application/json')
    .send({ unknow });

describe('shortUrl接口测试', () => {  
  test('shortUrl接口参数异常测试', async () => {
    const res = await createShortUrlErroeApi("https://www.baidu.com/rsv_t=b962RRXTbtksyRjfNOH1gNTeks%2BDGAAB64avxqan3wj8C0%2F6bjFrRdIDVFlUjVnp14k71");
    expect(res.statusCode).toEqual(200)
    expect(res.body.success).toEqual(false)
    expect(res.body.code).toEqual("SU_REQ_ARG_ERROR")
  })
})


// 测试originalUrl接口正确性
const createOriginalUrlApi = (shortUrl?: string) =>
  supertest(server_app)
    .post('/originalUrl')
    .set('Content-Type', 'application/json')
    .send({ shortUrl });

describe('originalUrl接口测试', () => {  
  test('originalUrl接口的正确性', async () => {
    const res = await createOriginalUrlApi("http://s.cn/Zi2M2ZZZ");
    expect(res.statusCode).toEqual(200)
    expect(res.body.code).toEqual("SU_OK")
  })
})

describe('originalUrl接口测试', () => {  
  test('originalUrl接口查询URL不存在测试', async () => {
    const res = await createOriginalUrlApi("http://s.cn/111111");
    expect(res.statusCode).toEqual(200)
    expect(res.body.success).toEqual(false);
    expect(res.body.code).toEqual("SU_SERVER_CAN_NOT_FIND_URL");
   })
})

describe('originalUrl接口测试', () => {  
  test('originalUrl接口查询,缓存不存在，查数据库存在', async () => {
    const res = await createOriginalUrlApi("http://s.cn/zeii7YZZ");
    expect(res.statusCode).toEqual(200)
    expect(res.body.success).toEqual(true);
    expect(res.body.code).toEqual("SU_OK");
   })
})

describe('originalUrl接口测试', () => {  
  test('originalUrl接口查询,再次查询，缓存已存在', async () => {
    const res = await createOriginalUrlApi("http://s.cn/zeii7YZZ");
    expect(res.statusCode).toEqual(200)
    expect(res.body.success).toEqual(true);
    expect(res.body.code).toEqual("SU_OK");
   })
})

// 异常originalUrl接口测试-参数异常
const createOriginalUrlErrorApi = (unknow?: string) =>
  supertest(server_app)
    .post('/originalUrl')
    .set('Content-Type', 'application/json')
    .send({ unknow });

describe('originalUrl接口测试', () => {  
  test('originalUrl接口异常测试', async () => {
    const res = await createOriginalUrlErrorApi("http://s.cn/ZjA6Ebaa");
    expect(res.statusCode).toEqual(200)
    expect(res.body.success).toEqual(false)
    expect(res.body.code).toEqual("SU_REQ_ARG_ERROR")
  })
})



// 
/*
describe('queryOrGenerateShortUrl Server', () => {
  it('should be able to add things correctly' , () => {
    expect(ShortUrlGenerator("www.baidu.com", 12).length).toBe(0);
  });
});


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