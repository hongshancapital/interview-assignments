import { short_url } from "../models/shorturl";
import { DatabaseProvider } from "./databaseprovider";
import { redisCache } from "../cache/redis"
import cfg from "../config/config";

export class ShortUrlDao {

    constructor() {}

    public async create(shortUrl: short_url)  {
        const newShortUrl = new short_url();
        newShortUrl.create_date = shortUrl.create_date;
        newShortUrl.original_url = shortUrl.original_url;
        newShortUrl.shorturl_id = shortUrl.shorturl_id;

        const connection = await DatabaseProvider.getConnection();
        let shortUrlResult = await connection.getRepository(short_url).save(newShortUrl);

        if (cfg.blUse) {
            await redisCache.BfAdd(newShortUrl.shorturl_id);
        }

        return shortUrlResult;
    }

    public async getByLongurl(strOriginalUrl: string)  {
        const connection = await DatabaseProvider.getConnection();
        const newShortUrl = new short_url();
        newShortUrl.original_url = strOriginalUrl;

        let shortUrlResult = await connection.getRepository(short_url).findOne(newShortUrl);

        return shortUrlResult;
    }

    public async getByShortUrlid(strShortID: string)  {
        const connection = await DatabaseProvider.getConnection();
        const newShortUrl = new short_url();
        newShortUrl.shorturl_id = strShortID;

        if (cfg.blUse) {
            let bfExists: Boolean = await redisCache.BfExists(strShortID);
            if (!bfExists) {
                return null;
            }
        }
        return await connection.getRepository(short_url).findOne(newShortUrl);
    }
}