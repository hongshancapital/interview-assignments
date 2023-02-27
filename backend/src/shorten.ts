import Redis from "ioredis";

const shortenkey = 'shorten:url';
const longkey = 'long:url';

export class Shorten {

	public static async shorten(url: string, redis: Redis): Promise<string> {
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