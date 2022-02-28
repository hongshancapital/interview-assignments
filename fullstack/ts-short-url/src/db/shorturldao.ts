import { ShortUrl } from "../models/shorturl";
import { DatabaseProvider } from "./databaseprovider";
import { redisCache } from "../cache/redis"
import cfgs from "../config/config";

export class ShortUrlDao {

    constructor() {}

    public async create(shortUrl: ShortUrl)  {
        const newShortUrl = new ShortUrl();
        newShortUrl.createdate = shortUrl.createdate;
        newShortUrl.originalurl = shortUrl.originalurl;
        newShortUrl.shorturlid = shortUrl.shorturlid;

        const connection = await DatabaseProvider.getConnection();
        let shortUrlResult = await connection.getRepository(ShortUrl).save(newShortUrl);

        if (cfgs.bl_use) {
            await redisCache.BfAdd(newShortUrl.shorturlid);
        }

        return shortUrlResult;
    }

    public async getByLongurl(strOriginalUrl: string)  {
        const connection = await DatabaseProvider.getConnection();
        const newShortUrl = new ShortUrl();
        newShortUrl.originalurl = strOriginalUrl;

        let shortUrlResult = await connection.getRepository(ShortUrl).findOne(newShortUrl);

        return shortUrlResult;
    }

    public async getByShortUrlid(strShortID: string)  {
        const connection = await DatabaseProvider.getConnection();
        const newShortUrl = new ShortUrl();
        newShortUrl.shorturlid = strShortID;

        if (cfgs.bl_use) {
            let bfExists: Boolean = await redisCache.BfExists(strShortID);
            if (!bfExists) {
                return null;
            }
        }

        let shortUrlResult  = await connection.getRepository(ShortUrl).findOne(newShortUrl);
        
        return shortUrlResult;
    }
}