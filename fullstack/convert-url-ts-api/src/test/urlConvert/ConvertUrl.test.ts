import Boot from '../../configuration/boot'
import request from 'supertest'
import dotenv from 'dotenv'
dotenv.config()
import { Server } from 'http'
import md5 from 'md5'
describe('http',()=>{
    let server:Server
    beforeAll(async ()=>{
        server = await Boot.run();
    })
    it('POST /convert/longToShort 正案例（网络状态为200且code为0000且返回的短域名(不包含域名)长度为8为）', async function () {
        let response = await request(server).post('/convert/longToShort').send({longUrl:'http://www.baidu.com/search?asder'}).set('content-type','application/json')
        expect(response.status).toEqual(200)
        expect(response.body.code).toEqual('0000')
        let reg = /[a-zA-Z]+:[/][/][a-zA-Z._-]+[/]/
        let domain:RegExpMatchArray|null= response.body.data.match(reg);
        expect(response.body.data.replace(domain,'').length).toEqual(8)
    });

    it('POST /convert/longToShort 反案例[参数非域名]（网络状态为415且code为F002且返回错误消息）', async function () {
        let response = await request(server).post('/convert/longToShort').send({longUrl:'adsdasd'}).set('content-type','application/json')
        expect(response.status).toEqual(415)
        expect(response.body.code).toEqual('F002')
        console.log(response.body)
    });
    it('POST /convert/longToShort 反案例[未传入指定参数]（网络状态为415且code为F001且返回错误消息）', async function () {
        let response = await request(server).post('/convert/longToShort').send({long1Url:'adsdasd'}).set('content-type','application/json')
        expect(response.status).toEqual(415)
        expect(response.body.code).toEqual('F001')
        console.log(response.body)
    });
    it('POST /convert/longToShort 反案例[xss漏洞]（网络状态为415且code为F002且返回错误消息）', async function () {
        let response = await request(server).post('/convert/longToShort').send({longUrl:'<script>alert(1);</script>'}).set('content-type','application/json')
        expect(response.status).toEqual(415)
        expect(response.body.code).toEqual('F002')
        console.log(response.body)
    });
    it('POST /convert/longToShort 反案例[sql注入]（网络状态为415且code为F002且返回错误消息）', async function () {
        let response = await request(server).post('/convert/longToShort').send({longUrl:'0 or 1=1'}).set('content-type','application/json')
        expect(response.status).toEqual(415)
        expect(response.body.code).toEqual('F002')
        console.log(response.body)
    });
    it('POST /convert/shortToLong 正案例（网络状态为200且code为0000且返回长域名）',async function() {
        let response = await request(server).post('/convert/longToShort').send({longUrl:'http://www.baidu.com/'+md5(''+Math.random())+'/'+Math.random()});
        expect(response.status).toEqual(200)
        expect(response.body.code).toEqual('0000')
        let shortUrl:string = response.body.data
        let res = await request(server).post('/convert/shortToLong').send({shortUrl:shortUrl})
        expect(res.status).toEqual(200)
        expect(res.body.code).toEqual('0000')
        console.log(res.body)
    })
    it('POST /convert/shortToLong 反案例[为传入指定字段](网络状态为415且code为F001)',async ()=>{
        let response = await request(server).post('/convert/shortToLong').send({shortUrl1:'asdd'})
        expect(response.status).toEqual(415);
        expect(response.body.code).toEqual('F001')
        console.log(response.body)
    });
    it('POST /convert/shortToLong 反案例[传入非域名](网络状态为415且code为F002)',async ()=>{
        let response = await request(server).post('/convert/shortToLong').send({shortUrl:'asdd'})
        expect(response.status).toEqual(415);
        expect(response.body.code).toEqual('F002')
        console.log(response.body)
    });
    it('POST /convert/shortToLong 反案例[传入短域名不存在](网络状态为415且code为B001)',async ()=>{
        let response = await request(server).post('/convert/shortToLong').send({shortUrl:'http://www.baidu.com/dnfgtlez'})
        expect(response.status).toEqual(415);
        expect(response.body.code).toEqual('B001')
        console.log(response.body)
    });
    it('POST /convert/shortToLong 反案例[xss注入](网络状态为415且code为F002)',async ()=>{
        let response = await request(server).post('/convert/shortToLong').send({shortUrl:'<script>alert(1)</script>'})
        expect(response.status).toEqual(415);
        expect(response.body.code).toEqual('F002')
        console.log(response.body)
    });
    it('POST /convert/shortToLong 反案例[sql注入](网络状态为415且code为B001)',async ()=>{
        let response = await request(server).post('/convert/shortToLong').send({shortUrl:'http://www.baidu.com/ascxzsdr?\' or 1=1'})
        expect(response.status).toEqual(415);
        expect(response.body.code).toEqual('B001')
        console.log(response.body)
    });
})