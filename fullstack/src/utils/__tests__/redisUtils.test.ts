import { redisUtil } from "../redisUtils"
import { client } from "../../server"

import { testData } from "../../mock/ShortUrl.mock"
const longTestData = {
	longUrl: testData.redisTestData.longUrl,
	expireTime: 100,
}
const shortUrl = testData.redisTestData.shortUrl
describe("redisUtils", () => {
	beforeAll(async () => {
		await client.connect()
	})

	afterAll(async () => {
		await redisUtil.deleteShortUrlMap(shortUrl)
		await client.disconnect()
	})

	it("test setUrlMap and getUrlMap", async () => {
		await redisUtil.setShortUrlMap(shortUrl, longTestData)
		const result = await redisUtil.getShortUrlMap(shortUrl)
		expect(result.longUrl).toEqual(longTestData.longUrl)
	})
	it("test delete ", async () => {
		await redisUtil.setShortUrlMap(shortUrl, longTestData)
		await redisUtil.deleteShortUrlMap(shortUrl)
		const result = await redisUtil.getShortUrlMap(shortUrl)
		expect(result).toEqual(null)
	})

	it("test expire ", async () => {
		await redisUtil.setShortUrlMap(shortUrl, longTestData)
		const re = await redisUtil.expireShortUrlMap(
			shortUrl,
			longTestData.expireTime
		)
		expect(re).toEqual(true)
	})
})
