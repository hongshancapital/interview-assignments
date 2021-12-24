import { BaseEntity } from 'typeorm';
export declare class ShortUrlMap extends BaseEntity {
    id: number;
    shortUrl: string;
    longUrl: string;
    createdTime: Date;
    expireTime: Date;
}
