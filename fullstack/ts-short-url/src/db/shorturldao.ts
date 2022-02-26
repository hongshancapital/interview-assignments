import { ShortUrl } from "../models/shorturl";
import { DatabaseProvider } from "./databaseprovider";

export class ShortUrlDao {

    constructor() {}

    public async create(shortUrl: ShortUrl)  {
        const newShortUrl = new ShortUrl();
        newShortUrl.createdata = shortUrl.createdata;
        newShortUrl.longurl = shortUrl.longurl;
        newShortUrl.shorturlid = shortUrl.shorturlid;

        const connection = await DatabaseProvider.getConnection();
        return await connection.getRepository(ShortUrl).save(newShortUrl);
    }

    public async getByLongurl(strLong: string)  {
        const connection = await DatabaseProvider.getConnection();
        const newShortUrl = new ShortUrl();
        newShortUrl.longurl = strLong;
        return await connection.getRepository(ShortUrl).findOne(newShortUrl);
    }

    public async getByShortUrlid(strShortID: string)  {
        const connection = await DatabaseProvider.getConnection();
        const newShortUrl = new ShortUrl();
        newShortUrl.shorturlid = strShortID;
        return await connection.getRepository(ShortUrl).findOne(newShortUrl);
    }
}