import { ShortUrl, ShortUrlModel } from '../models/shortUrl';
import { prisma as dbconn } from './db';
import { Prisma } from '.prisma/client'

export class ShortUrlRepo {
	async findUnique(where: Prisma.short_urlsWhereUniqueInput): Promise<ShortUrlModel | null> {
		const shortUrl: ShortUrl | null = await dbconn.short_urls.findUnique({ where })
		if (shortUrl === null) {
			return null;
		}
		let shortUrlModel: ShortUrlModel = new ShortUrlModel();
		shortUrlModel.id = shortUrl.id;
		shortUrlModel.long_url = shortUrl.long_url;
		shortUrlModel.created_at = shortUrl.created_at;
		return shortUrlModel;
	}

	async create(data: Prisma.short_urlsCreateInput): Promise<ShortUrlModel> {
		const shortUrl: ShortUrl = await dbconn.short_urls.create({ data })
		let shortUrlModel: ShortUrlModel = new ShortUrlModel();
		shortUrlModel.id = shortUrl.id;
		shortUrlModel.long_url = shortUrl.long_url;
		shortUrlModel.created_at = shortUrl.created_at;
		return shortUrlModel;
	}

	async delete(args: Prisma.short_urlsDeleteArgs): Promise<ShortUrlModel> {
		const shortUrl: ShortUrl = await dbconn.short_urls.delete(args)
		let shortUrlModel: ShortUrlModel = new ShortUrlModel();
		shortUrlModel.id = shortUrl.id;
		shortUrlModel.long_url = shortUrl.long_url;
		shortUrlModel.created_at = shortUrl.created_at;
		return shortUrlModel;
	}
}