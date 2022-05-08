import { ShortUrlService } from "../service/shortUrlService"

class ShortUrlMock {
	constructor() {}

	public async add(longUrl: string) {
		const shortUrlService = new ShortUrlService()
		const re = await shortUrlService.longToShortUrl(longUrl)
	}

	public async delete(longUrl: string) {
		const shortUrlService = new ShortUrlService()
		const shortUrl = await shortUrlService.getShortUrlByLongUrl(longUrl)
		await shortUrlService.deleteShortUrlMap(shortUrl)
	}
}
export const shortUrlMock = new ShortUrlMock()
export const testData = {
	exits: {
		shortUrl: "http://www.baidu.com/3kb2uL",
		shortHash: "3kb2uL",
		shortHash2: "2vGjSq",
		shortHash3: "3VZfgg",
		longUrl: "http://www.baidu.com",
		domain: "http://www.baidu.com",
	},
	notExits: {
		shortUrl: "http://www.google.com/2gSW1b",
		shortHash: "2gSW1b",
		longUrl: "http://www.google.com",
	},
	redisTestData: {
		shortUrl: "http://www.redis.com/abc",
		shortHash: "abc",
		longUrl: "http://www.redis.com",
	},
}
