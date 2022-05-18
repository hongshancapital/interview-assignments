/**
 * unit.test.ts
 * @authors lizilong
 * @description 单元测试文件
 */

import server from './testServer'
import tool from './utils/tool'

// 测试普通方法，输入域名是否合法OK
test('输入域名是否合法OK', () => {
	const flag = tool.checkUrl("https:www.bac.com")
	expect(flag).toBe(false)
})

// 测试普通方法，例如短域名生成长度为8个字符
test('短域名生成长度为8个字符OK', () => {
	const shorturl = tool.shortUrlGenerator()
	console.log(`shorturl = ${shorturl}`)
	const res = shorturl.length
	expect(res).toBe(8)
})

// 测试调用携带参数的POST接口，长域名转换为短域名并存储落库的接口API
test('长域名转换为短域名OK', async () => {
	const res = await server.post('/api/urlLongToShort').send({
		longUrl: 'https://github.com/FredII/shortUrl/tree/master/fullstack',
	})

	// 添加断言toBe是比较值
	expect(res.body.msg).toBe('ok')
})

// 测试调用GET接口，短域名重定向为长域名的接口API
test('短域名重定向为长域名OK', async () => {
	const res = await server.get('/9a5094fa')
	// 添加断言toEqual是比较对象
	expect(res.body).toEqual({})
})
