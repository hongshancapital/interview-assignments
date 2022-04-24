import { client } from "../server"

class RedisUtil {
	public async set(
		key: string,
		value: string,
		expireTime?: number
	): Promise<void> {
		await client.set(key, value)
		if (expireTime) {
			await client.expire(key, expireTime)
		}
	}

	public async get(key: string): Promise<string> {
		return await client.get(key)
	}

	public async delete(key: string): Promise<number> {
		return await client.del(key)
	}

	public async expire(key: string, expireTime: number): Promise<boolean> {
		return await client.expire(key, expireTime)
	}

	public async setShortUrlMap(
		shortUrl: string,
		longUrl: {
			longUrl: string
			expireTime: number
		},
		expireTime?: number
	): Promise<void> {
		return await this.set(
			`shortUrl:${shortUrl}`,
			JSON.stringify(longUrl),
			expireTime
		)
	}

	public async getShortUrlMap(shortUrl: string): Promise<{
		longUrl: string
		expireTime: number
	}> {
		const re = await this.get(`shortUrl:${shortUrl}`)
		if (re) {
			return JSON.parse(re)
		}
		return null
	}

	public async deleteShortUrlMap(shortUrl: string): Promise<number> {
		return await this.delete(`shortUrl:${shortUrl}`)
	}

	public async expireShortUrlMap(
		shortUrl: string,
		expireTime: number
	): Promise<boolean> {
		return await this.expire(`shortUrl:${shortUrl}`, expireTime)
	}
}

export const redisUtil = new RedisUtil()
