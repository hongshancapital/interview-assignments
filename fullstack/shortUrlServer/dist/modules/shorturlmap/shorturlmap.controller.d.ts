import { ShortUrlMapService } from './shorturlmap.service';
import { HashService } from '../../services/hash.service';
import { RedisService } from '../../services/redis.service';
import { BloomFilterService } from '../../services/bloomFilter.service';
interface ICreateShortUrlReq {
    longUrl: string;
    expireTime?: number;
}
interface IDeleteShortUrlMapReq {
    shortUrl?: string;
}
interface IGetLongUrlRes {
    data: string;
}
interface ICreateShortUrlRes {
    data: string;
}
export declare class ShortUrlMapController {
    private readonly shortUrlService;
    private readonly hashServive;
    private readonly redisService;
    private readonly bloomFilterService;
    constructor(shortUrlService: ShortUrlMapService, hashServive: HashService, redisService: RedisService, bloomFilterService: BloomFilterService);
    getLongUrl(query?: {
        shortUrl?: string;
    }): Promise<IGetLongUrlRes>;
    deleteShortUrlMap(body: IDeleteShortUrlMapReq): Promise<void>;
    createShortUrl(body: ICreateShortUrlReq, req: any): Promise<ICreateShortUrlRes>;
    private idToShortUrl;
    private shortUrlToId;
}
export {};
