import originUrlModel from "./originUrlModel.js";
import util from './util.js';

class UrlService {
    async longToShort(longUrl: string): Promise<string> {
        const cacheResult = await originUrlModel.getShortFromCache(longUrl);
        if (cacheResult) {
            return util.buildShortUrl(cacheResult);
        }
        let id = await originUrlModel.getIdByLongUrl(longUrl);
        if (!id) {
            id = await originUrlModel.addUrl(longUrl);
        }
        const path = util.calcIdToPath(id);
        const result = util.buildShortUrl(path);
        originUrlModel.cacheLongToShort(path, longUrl)
        return result;
    }

    async shortToLong(path: string): Promise<string | null> {
        const cacheResult = await originUrlModel.getLongFromCache(path);
        if (cacheResult) {
            return cacheResult;
        }
        const id = util.calcPathToId(path);
        console.log('path to id calc:', { path, id });
        const dbResult = await originUrlModel.getLongUrlById(id);
        if (dbResult) {
            originUrlModel.cacheShortToLong(path, dbResult)
        }
        return dbResult;
    }
}

export default new UrlService();
