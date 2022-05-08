import { shortUrlUtils } from "../shortUrlUtils"

describe("test shortUrlUtil", () => {
	it("test generateShortHash", () => {
		const hash = shortUrlUtils.generateShortHash("http://www.baidu.com")
		const hash2 = shortUrlUtils.generateShortHash("http://www.baidu.com")
		const hash3 = shortUrlUtils.generateShortHash("http://www.zhihu.com")
		const hash4 = shortUrlUtils.generateShortHash("")

		expect(hash.length).toBe(6)
		expect(hash2.length).toBe(6)
		expect(hash3.length).toBe(6)
		expect(hash4).toBe(null)

		expect(hash).toEqual(hash2)
		expect(hash).not.toEqual(hash3)
	})

	it("test generateShortUrl", () => {
		const url1 = shortUrlUtils.generateShortUrl(
			"http://www.baidu.com/as?a=2",
			"abcdef"
		)
		const url2 = shortUrlUtils.generateShortUrl(
			"http://www.baidu.com/222/23233",
			"abcdef"
		)
		const url3 = shortUrlUtils.generateShortUrl(
			"http://www.zhihu.com",
			"abcdef"
		)
		const url4 = shortUrlUtils.generateShortUrl("http://www.zhihu.com", "")
		const url5 = shortUrlUtils.generateShortUrl("", "")

		expect(url1).toEqual("http://www.baidu.com/abcdef")
		expect(url2).toEqual("http://www.baidu.com/abcdef")
		expect(url3).toEqual("http://www.zhihu.com/abcdef")
		expect(url4).toEqual("http://www.zhihu.com/")
		expect(url5).toBe(null)
	})

	it("test isValidUrl", () => {
		const url1 = shortUrlUtils.isValidUrl("http://www.baidu.com/as?a=2")
		const url2 = shortUrlUtils.isValidUrl("http://www.baidu.com/222/23233")
		const url3 = shortUrlUtils.isValidUrl("http://www.zhihu.com")
		const url4 = shortUrlUtils.isValidUrl("")
		const url5 = shortUrlUtils.isValidUrl("http://")
		const url6 = shortUrlUtils.isValidUrl("http://absd.com")
		const url7 = shortUrlUtils.isValidUrl("asdjajd2")

		expect(url1).toEqual(true)
		expect(url2).toEqual(true)
		expect(url3).toEqual(true)
		expect(url4).toEqual(false)
		expect(url5).toBe(false)
		expect(url6).toBe(true)
		expect(url7).toBe(false)
	})
})
