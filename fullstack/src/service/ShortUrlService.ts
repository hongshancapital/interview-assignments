import { ShortUrlMap } from "../entity/ShortUrlMap"
import { AppDataSource } from "../data-source"
import { shortUrlUtils } from "../utils/shortUrlUtils"
import * as dayjs from "dayjs"
import { DeleteResult } from "typeorm"
import { redisUtil } from "../utils/redisUtils"

export class ShortUrlService {
	// 判断长链接是否在数据库中
	public async isLongUrlExist(longUrl: string): Promise<boolean> {
		return (
			(await AppDataSource.manager.findOne(ShortUrlMap, {
				where: {
					longUrl: longUrl,
				},
			})) != null
		)
	}

	// 判断短链接是否在数据库中
	public async isShortUrlExist(shortUrl: string): Promise<boolean> {
		return (
			(await AppDataSource.manager.findOne(ShortUrlMap, {
				where: {
					shortUrl,
				},
			})) != null
		)
	}

	// 判断短链接hash是否在数据库中
	public async isShortHashExist(shortHash: string): Promise<boolean> {
		const re = await AppDataSource.manager.findOne(ShortUrlMap, {
			where: {
				shortHash,
			},
		})
		return re !== null
	}

	// 判断缓存是否过期

	// 根据短链接获取长链接
	public async getLongUrlByShortUrl(shortUrl: string): Promise<string> {
		// 这里缓存拦截一道
		const cacheLongUrl = await redisUtil.getShortUrlMap(shortUrl)

		if (cacheLongUrl) {
			const cacheLongUrlObj = cacheLongUrl

			if (cacheLongUrlObj.expireTime > dayjs().unix()) {
				// 刷新过期时间
				await redisUtil.expireShortUrlMap(
					shortUrl,
					dayjs().add(30, "day").unix()
				)
				return cacheLongUrlObj.longUrl
			} else {
				// 配合定时任务删除过期短链接
				await this.deleteShortUrlMap(shortUrl)
				return null
			}
		}
		const shortUrlMap = await AppDataSource.manager.findOne(ShortUrlMap, {
			where: {
				shortUrl,
			},
		})
		if (shortUrlMap) {
			// 存储短链接到redis
			await redisUtil.setShortUrlMap(
				shortUrl,
				{
					longUrl: shortUrlMap.longUrl,
					expireTime: dayjs(shortUrlMap.expireTime).unix(),
				},
				dayjs().add(30, "day").unix()
			)
			return shortUrlMap.longUrl
		}
		return null
	}

	// 删除短链接映射
	public async deleteShortUrlMap(shortUrl: string): Promise<boolean> {
		const deleteResult: DeleteResult = await AppDataSource.manager.delete(
			ShortUrlMap,
			{
				shortUrl,
			}
		)
		if (deleteResult.affected && deleteResult.affected > 0) {
			const redisDeleteResult = await redisUtil.deleteShortUrlMap(
				shortUrl
			)
			if (redisDeleteResult) {
				return true
			}
		}
		return false
	}

	// 根据长链接获取短链接
	public async getShortUrlByLongUrl(longUrl: string): Promise<string> {
		const shortUrlMap = await AppDataSource.manager.findOne(ShortUrlMap, {
			where: {
				longUrl,
			},
		})
		if (shortUrlMap) {
			return shortUrlMap.shortUrl
		}
		return null
	}

	// 根据长链接获取链接实体
	public async getShortUrlMapByLongUrl(
		longUrl: string
	): Promise<ShortUrlMap> {
		return await AppDataSource.manager.findOne(ShortUrlMap, {
			where: {
				longUrl,
			},
		})
	}

	// 根据长链接生成短链接hash
	public async generateShortUrlHash(
		longUrl: string,
		count: number = 0
	): Promise<string> {
		if (count > 3) {
			throw new Error("generateShortUrlHash over 3 times error")
		}
		// 这里需要加上种子否则生成的hash值会一样
		const shortUrlHash = shortUrlUtils.generateShortHash(longUrl, count)
		// 处理冲突
		if (!(await this.isShortHashExist(shortUrlHash))) {
			return shortUrlHash
		}
		// 生成短链接失败，重新生成,超过三次直接报错
		return this.generateShortUrlHash(longUrl, count + 1)
	}

	// 保存短链接并返回短链接实体
	public async longToShortUrl(longUrl: string): Promise<ShortUrlMap> {
		const exitsShortUrl = await this.getShortUrlMapByLongUrl(longUrl)
		if (exitsShortUrl) {
			return exitsShortUrl
		}
		// 生成短链接
		const shortHash = await this.generateShortUrlHash(longUrl)

		const shortUrlMap = new ShortUrlMap()
		shortUrlMap.shortUrl = shortUrlUtils.generateShortUrl(
			longUrl,
			shortHash
		)
		shortUrlMap.longUrl = longUrl
		shortUrlMap.expireTime = dayjs().add(5, "year").toDate()
		shortUrlMap.shortHash = shortHash
		const result = await AppDataSource.manager.save(
			ShortUrlMap,
			shortUrlMap
		)

		return result
	}
}
