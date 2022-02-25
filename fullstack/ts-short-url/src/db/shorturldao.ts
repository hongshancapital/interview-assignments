import { ShortUrl } from "../models/shorturl";
import { DatabaseProvider } from "./databaseprovider";
import cfgs  from "../config/config"

export class ShortUrlDao {

    constructor() {}

    public async create(shortUrl: ShortUrl): Promise<ShortUrl> {
        const newShortUrl = new ShortUrl();
        newShortUrl.createdata = shortUrl.createdata;
        newShortUrl.longurl = shortUrl.longurl;
        newShortUrl.shorturlid = shortUrl.shorturlid;

        const connection = await DatabaseProvider.getConnection();
        return await connection.getRepository(ShortUrl).save(newShortUrl);
    }

    public async getByLongurl(strLong: string): Promise<ShortUrl> {
        const connection = await DatabaseProvider.getConnection();
        let strSQL = "select * from " + cfgs.db_name + ".SHORTURL" + "where LONGURL = " + strLong;
        return await connection.getRepository(ShortUrl).query(strSQL);    
    }

    public async getByShortUrlid(strShortID: string): Promise<ShortUrl> {
        const connection = await DatabaseProvider.getConnection();
        let strSQL = "select * from " + cfgs.db_name + ".SHORTURLID" + "where LONGURL = " + strShortID;
        return await connection.getRepository(ShortUrl).query(strSQL);   
    }
}