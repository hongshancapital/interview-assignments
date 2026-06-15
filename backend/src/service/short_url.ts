import BaseService from "./base";
import { IContext } from "../types/common";
import { UrlEntity } from "../entity/Url";
import {ResourceNotFound} from "../middleware/error";

export class ShortUrlService extends BaseService{
    constructor(ctx: IContext) {
        super(ctx);
    }

    /**
     * 插入长域名, 返回短域名信息
     * @param url {string}
     * @return {string}
     */
    public async insertUrl (url: string): Promise<string> {
        // 设置为最高的事务隔离等级
        return this.app.mysqlDataSource.transaction('SERIALIZABLE', async (t) => {
            const urlRepo = t.getRepository(UrlEntity);
            const existUrl = await urlRepo.findOne({
                where: {
                    longUrl: url
                }
            });

            if (existUrl) {
                return `${this.config.shortUrlServer}/${existUrl.shortUrl}`;
            }

            const newUrl = new UrlEntity('', url);
            const insertResult = await urlRepo.insert(newUrl);
            const newId = insertResult.identifiers[0] && insertResult.identifiers[0].id as (number | undefined);

            if (!newId) {
                throw new Error('');
            }

            const shortUrl = this.string10to62(10000 + newId);

            await urlRepo.update(newId, new UrlEntity(shortUrl, url));

            return `${this.config.shortUrlServer}/${shortUrl}`;
        });
    }

    /**
     * 根据短域名, 查找对应的长域名
     * @param shortUrl {string}
     * @return {string}
     */
    public async find (shortUrl: string) {
        const urlResult = await this.app.mysqlDataSource.getRepository(UrlEntity).findOne({
            where: {
                shortUrl: shortUrl.replace(`${this.config.shortUrlServer}/`, '')
            }
        });

        if (!urlResult) {
            throw new ResourceNotFound('short_url not found');
        }

        return urlResult.longUrl;
    }

    /**
     * 数字62进制编码
     * @param count {number}
     * @return {string}
     */
    public string10to62(count: number) {
        const chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ'.split('');
        const radix = chars.length;
        let qutient = +count;
        const arr = [];
        do {
            const mod = qutient % radix;
            qutient = (qutient - mod) / radix;
            arr.unshift(chars[mod]);
        } while (qutient);
        return arr.join('');
    }
}
