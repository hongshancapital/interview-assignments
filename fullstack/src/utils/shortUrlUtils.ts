import * as base62 from "base62"
import * as murmurhash from "murmurhash"

class ShortUrlUtils {
	public generateShortHash(longUrl: string, count?: number): string {
		if (longUrl) {
			const hash = murmurhash.v3(
				longUrl,
				count > 0 ? new Date().getTime() + count : undefined
			)
			return base62.encode(hash)
		}
		return null
	}

	// 根据短链接hash和长链接生成短链接
	public generateShortUrl(longUrl: string, hash: string): string {
		if (longUrl && this.isValidUrl(longUrl)) {
			const longUrlObj = new URL(longUrl)
			const shortUrl = longUrlObj.origin + "/" + hash
			return new URL(hash, shortUrl).href
		}
		return null
	}

	// 判断链接是否有效
	public isValidUrl(url: string): boolean {
		if (url && (url.startsWith("http://") || url.startsWith("https://"))) {
			try {
				const urlObj = new URL(url)
				return true
			} catch (e) {
				return false
			}
		}
		return false
	}
}
export const shortUrlUtils = new ShortUrlUtils()
