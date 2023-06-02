import Redis from "ioredis";

const shortenkey = 'shorten:url'; // 短域名hash
const longkey = 'long:url'; // 长域名hash

export class Shorten {

	public static async shorten(url: string, redis: Redis): Promise<string> {
		// 长短url都通过hash结构对应存储，避免重复创建，方便查找
		let code = await redis.hget(longkey, url);
		if (code) return code;
		code = Math.random().toString(36).slice(-8)
		await redis.hset(shortenkey, code, url);
		await redis.hset(longkey, url, code);
		return code;
	}

	public static async expand(code: string, redis: Redis): Promise<string | null> {
		const url = await redis.hget(shortenkey, code);
		if (url) return url;
		return null;
	}

}