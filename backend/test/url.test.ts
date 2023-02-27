import redis from '../src/redis';
import request from 'supertest';

const fetch = request('http://127.0.0.1:3005');

describe('短域名存储', () => {
	beforeEach(async () => {
		await redis.flushall();
	});

	it('请求错误格式url', async () => {
		const response = await fetch.post('/shorten').send({ url: "**********" });
		expect(response.status).toEqual(400);
	});

	it('请求缺省格式url', async () => {
		const response = await fetch.post('/shorten').send({ url: "github.com/scdt-china/interview-assignments" });
		expect(response.status).toEqual(400);
	});

	it('请求正确的url', async () => {
		const response = await fetch.post('/shorten').send({ url: "https://github.com/scdt-china/interview-assignments" });
		expect(response.status).toEqual(200);
		expect(response.body).toEqual({
			shortUrl: expect.any(String),
			code: expect.any(String),
		});
		expect(response.body.code.length).toBe(8);

		await redis.set('token', response.body.code);
	});
});

describe('短域名读取', () => {
	it('请求不存在的短域名', async () => {
		const response = await fetch.get('/test');
		expect(response.status).toEqual(404);
	});

	it('请求存在的短域名', async () => {
		const code = await redis.get('token');
		const response = await fetch.get(`/${code}`);
		expect(response.status).toBe(302);
		expect(response.headers.location).toBe('https://github.com/scdt-china/interview-assignments');
	});
})

describe('集成测试', () => {
	const testurl = async (url: string) => {
		const response = await fetch.post('/shorten').send({ url });
		expect(response.status).toBe(200);
		expect(response.body).toEqual({
			shortUrl: expect.any(String),
			code: expect.any(String),
		});
		expect(response.body.code.length).toBe(8);

		const resp = await fetch.get(`/${response.body.code}`);
		expect(resp.status).toBe(302);
		expect(resp.headers.location).toBe(url);
	};

	it('创建了一个短域名，并用短域名跳转', async () => {
		await testurl('https://www.zhihu.com/');
	});

	it('创建一组短域名并逐个进行跳转', async () => {
		const urls = [
			'https://gitee.com/',
			'https://github.com/',
			'https://juejin.cn/',
			'https://segmentfault.com/',
			'https://stackoverflow.com/',
			'https://cnodejs.org/',
			'https://www.baidu.com/',
			'https://www.bilibili.com/',
			'https://v.qq.com/'
		]
		for (const url of urls) {
			testurl(url);
		}
	});
})