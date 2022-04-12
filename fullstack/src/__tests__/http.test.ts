import supertest from 'supertest';
import app from '../index';

function sendReq(body: Object = {}) {
    return supertest(app)
        .post('/long2short')
        .set('Content-Type', 'application/json')
        .send(body);
}

let long = 'https://github.com/scdt-china/interview-assignments';
let short: string;

describe('long2short 接口', () => {
    it('不传long参数 返回异常', async () => {
        const res = await sendReq();
        expect(res.statusCode).toEqual(200);
        expect(res.body).toEqual({ success: false, msg: '缺少参数long' })
    })

    it('long参数 格式不符合url 返回异常', async () => {
        const res = await sendReq({ long: 'httpbaidu.com' });
        expect(res.statusCode).toEqual(200);
        expect(res.body).toEqual({ success: false, msg: '参数long不是正确的url格式' })
    })

    it('参数正确请求 返回短连接', async () => {
        const res = await sendReq({ long });
        short = res.body?.data;
        expect(res.statusCode).toEqual(200);
        expect(res.body?.success).toEqual(true);
        expect(res.body?.data).toMatch(/^[A-Za-z0-9]{8}$/);
    })
});

describe('short2long 接口', () => {
    it('传入错误的短连接,返回404', async () => {
        const res = await supertest(app).get('/dqw2e3fds');
        expect(res.statusCode).toEqual(404);
    })

    it('传入正确的短链接,302到长链接', async () => {
        const res = await supertest(app).get('/'+short);
        expect(res.statusCode).toEqual(302);
        expect(res.headers.location).toEqual(long);
    })
})

