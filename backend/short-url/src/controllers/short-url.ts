import { ShortUrl } from 'src/entities/short-url';
import nanoid from 'utils/nanoid';
import { dataSource } from 'utils/database';

async function create(longUrl: string, length = 8): Promise<ShortUrl> {
    // FIXME: to vo
    const doc = await dataSource.getRepository(ShortUrl).findOneBy({ longUrl });

    if (doc) {
        return doc;
    }

    // handle collisions
    let shortUrl = nanoid(length);

    return dataSource.getRepository(ShortUrl).create({ shortUrl, longUrl });
}

async function findByShortUrl(shortUrl: string): Promise<ShortUrl> {
    return dataSource.getRepository(ShortUrl).findOneBy({ shortUrl });
}

export default {
    create,
    findByShortUrl
};
