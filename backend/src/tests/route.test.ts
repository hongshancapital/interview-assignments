import request from "supertest";
import {router} from "../route";
import express from 'express';
const app:express.Application = express();
app.use(express.json());

import {getOriginUrl, createShortUrl} from "../controller";
jest.mock('../controller')

const mockedGetOriginUrl  = jest.mocked(getOriginUrl,{shallow:true})
const mockedCreateShortUrl  = jest.mocked(createShortUrl,{shallow:true})

describe("POST /l2s",()=>{
    it('should create url success with valid payload',async()=>{
        mockedCreateShortUrl.mockResolvedValue('http://short.url/F1nrXpH')
        const res = await request(app.use(router)).post('/l2s').send({"originUrl":"https://github.com/ladjs/supertest"}).set({'Content-Type':'application/json'})
        expect(res.statusCode).toEqual(200);
        expect(res.text).toEqual('http://short.url/F1nrXpH')
    })
    it('should return 400 with invalid payload',async()=>{
        mockedCreateShortUrl.mockResolvedValue('http://short.url/F1nrXpH')
        const res = await request(app.use(router)).post('/l2s').send({"originUrl":"ftp://github.com/ladjs/supertest"}).set({'Content-Type':'application/json'})
        expect(res.statusCode).toEqual(400);
        expect(res.text).toEqual('no origin url or invalid url')
    })
    it('should return 400 with invalid payload ',async()=>{
        mockedCreateShortUrl.mockResolvedValue('http://short.url/F1nrXpH')
        const res = await request(app.use(router)).post('/l2s')
        expect(res.statusCode).toEqual(400);
        expect(res.text).toEqual('no origin url or invalid url')
    })
    it('should return 500 on create',async()=>{
        mockedCreateShortUrl.mockRejectedValue('a random error')
        const res = await request(app.use(router)).post('/l2s').send({"originUrl":"https://github.com/ladjs/supertest"}).set({'Content-Type':'application/json'})
        expect(res.statusCode).toEqual(500);
    })
})



describe("GET /s2l",()=>{
    it('should return 400 with no id providied',async()=>{
        const res = await request(app.use(router)).get('/s2l')
        expect(res.status).toEqual(400)
    })
    it('should return long url with id providied',async()=>{
        const mockResult = {
            "orginUrl":"https://github.com/expressjs/express/blob/master/examples/mvc/controllers/user/index.js"
        }
        mockedGetOriginUrl.mockResolvedValue(mockResult)
        const res = await request(app.use(router)).get('/s2l?sid=F4MzEIp')
        expect(res.statusCode).toEqual(200);
        expect(res.body).toEqual(mockResult);
    })
    it('should return 500 on query',async()=>{
 
        mockedGetOriginUrl.mockRejectedValue('any internal error')
        const res = await request(app.use(router)).get('/s2l?sid=F4MzEIp')
        expect(res.statusCode).toEqual(500);
    })
})

