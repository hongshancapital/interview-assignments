import { Repository } from 'typeorm';
import { ShortUrlMap } from './shorturlmap.entity';
export interface IShortUrlMap {
    shortUrl: string;
    longUrl: string;
    createTime: string;
}
export declare class ShortUrlMapService {
    private readonly primaryShortUrlMapRepository;
    private readonly backUpShortUrlMapRepository;
    constructor(primaryShortUrlMapRepository: Repository<ShortUrlMap>, backUpShortUrlMapRepository: Repository<ShortUrlMap>);
    createShortUrlMap(params: {
        flag: string;
        longUrl?: string;
        expireTime?: Date;
        createdTime?: Date;
    }): Promise<number>;
    updateShortUrlMapById(params: {
        id: number;
        updateData: {
            shortUrl?: string;
            longUrl?: string;
        };
        flag: string;
    }): Promise<import("typeorm").UpdateResult>;
    queryShortUrlMap(params: {
        shortUrl?: string;
        longUrl?: string;
        id?: number;
        flag?: string;
    }): Promise<ShortUrlMap[]>;
    deleteShortUrlMap(params: {
        shortUrl?: string;
        flag?: string;
    }): Promise<import("typeorm").DeleteResult>;
    getDbByFlag(flag: string): Repository<ShortUrlMap>;
}
