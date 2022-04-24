import { ShortUrlService } from "../ShortUrlService"
import "reflect-metadata"
import { bootstrap } from "../../server"
import { shortUrlMock, testData } from "../../mock/ShortUrl.mock"
import { redisUtil } from "../../utils/redisUtils"

let server
let app
let dataSource
let client

describe("test shortUrlService", () => {
	beforeAll(async () => {
		const myApp = await bootstrap()
		app = myApp.app
		server = myApp.server
		dataSource = myApp.dataSource
		client = myApp.client
		// await shortUrlMock.delete(testData.exits.longUrl)
		await shortUrlMock.add(testData.exits.longUrl)
	})
	afterAll(async () => {
		await shortUrlMock.delete(testData.exits.longUrl)

		await server.close()
		await dataSource.destroy()
		await client.disconnect()
	})

	it("test isShortHashExist", async () => {
		const shortUrlService = new ShortUrlService()
		const notExits = await shortUrlService.isShortHashExist(
			testData.notExits.shortHash
		)

		expect(notExits).toBe(false)

		const exits = await shortUrlService.isShortHashExist(
			testData.exits.shortHash
		)
		expect(exits).toBe(true)
	})

	it("test isShortUrlExist", async () => {
		const shortUrlService = new ShortUrlService()
		const shortUrlExist = await shortUrlService.isShortUrlExist(
			testData.notExits.shortUrl
		)
		expect(shortUrlExist).toBe(false)
		const shortUrlExist2 = await shortUrlService.isShortUrlExist(
			testData.exits.shortUrl
		)
		expect(shortUrlExist2).toBe(true)
	})

	it("test isLongUrlExist", async () => {
		const shortUrlService = new ShortUrlService()
		const urlNotExist = await shortUrlService.isLongUrlExist(
			testData.notExits.longUrl
		)
		expect(urlNotExist).toBe(false)
		const urlExist = await shortUrlService.isLongUrlExist(
			testData.exits.longUrl
		)
		expect(urlExist).toBe(true)
	})

	it("test getShortUrlMapByLongUrl", async () => {
		const shortUrlService = new ShortUrlService()
		const urlObj = await shortUrlService.getShortUrlMapByLongUrl(
			testData.exits.longUrl
		)
		expect(urlObj?.shortHash).toBe("3kb2uL")
		return urlObj
	})

	it("test getShortUrlByLongUrl", async () => {
		const shortUrlService = new ShortUrlService()
		const shortUrl = await shortUrlService.getShortUrlByLongUrl(
			testData.exits.longUrl
		)
		expect(shortUrl).toBe(testData.exits.shortUrl)

		const shortUrl2 = await shortUrlService.getShortUrlByLongUrl(
			testData.notExits.longUrl
		)
		expect(shortUrl2).toBe(null)
	})

	it("test getLongUrlByShortUrl", async () => {
		const shortUrlService = new ShortUrlService()
		const longUrl = await shortUrlService.getLongUrlByShortUrl(
			testData.exits.shortUrl
		)
		expect(longUrl).toBe(testData.exits.longUrl)
		const longUrl2 = await shortUrlService.getLongUrlByShortUrl(
			testData.notExits.shortUrl
		)
		expect(longUrl2).toBe(null)

		await redisUtil.setShortUrlMap(
			testData.notExits.shortUrl,
			{
				longUrl: testData.notExits.longUrl,
				expireTime: 1,
			},
			1
		)

		const expireUrl = await shortUrlService.getLongUrlByShortUrl(
			testData.notExits.shortUrl
		)
		expect(expireUrl).toBe(null)
	})

	it("test generateShortUrlHash", async () => {
		const shortUrlService = new ShortUrlService()
		const hash = await shortUrlService.generateShortUrlHash(
			testData.notExits.longUrl
		)
		expect(hash).toBe("2gSW1b")
		// 冲突一次的情况
		const hash1 = await shortUrlService.generateShortUrlHash(
			testData.exits.longUrl
		)
		expect(hash1).not.toBe(hash)
		// 冲突俩次的情况
		const hash2 = await shortUrlService.generateShortUrlHash(
			testData.exits.longUrl,
			2
		)
		expect(hash2).not.toBe(hash1)

		// 冲突三次的情况
		const hash3 = await shortUrlService.generateShortUrlHash(
			testData.exits.longUrl,
			3
		)
		expect(hash3).not.toBe(hash2)
		// 冲突四次的情况
		try {
			const hash4 = await shortUrlService.generateShortUrlHash(
				testData.exits.longUrl,
				4
			)
		} catch (error) {
			expect(error.message).toBe(
				"generateShortUrlHash over 3 times error"
			)
		}
	})

	//
	it("test saveShortUrl", async () => {
		const shortUrlService = new ShortUrlService()
		try {
			const shortUrl = await shortUrlService.longToShortUrl(
				testData.exits.longUrl
			)
			expect(shortUrl?.shortHash).toBe(testData.exits.shortHash)
		} catch (err) {
			expect(err.message).toBe("长链接已有短链接")
		}
	})
})
