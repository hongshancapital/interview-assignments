import murmurhash from 'murmurhash';

import { Domains } from '@/models';
import { isURL, num10To62 } from '@/utils';

export default new class DomainService {
    constructor() {
        //
    }

    /** 长链接转短链接 */
    private generateShortDomain(url: string): string {
        const pre = new URL(url).origin;
        /**
         * 从快速，低碰撞，均匀散列方面考虑，使用murmurhash算法计算hash
         * 将计算的hash值转换为62进制字符串以达到缩短字符串的目的
         */
        const compressed = num10To62(murmurhash.v3(url));

        if (compressed.length > 8) {
            throw Error('url is too long.');
        }

        return `${pre}/${compressed}`;
    }

    async addDomain(url: string, name?: string) {
        if (!isURL(url)) {
            throw Error(`invalid domain: ${url}.`);
        }
        const shortUrl = this.generateShortDomain(url);

        // 数据库为compressed字段添加了唯一索引，不做这个判断也可以，只是数据库抛出的错误不友好
        if (await Domains.findOne({ compressed: shortUrl })) {
            throw Error(`dimain info is already exist: ${url}`);
        }

        return await Domains.create({ name, url, compressed: shortUrl });
    }

    async getDomainByShortUrl(shortUrl: unknown) {
        if (
            !isURL(shortUrl) ||
            new URL(<string>shortUrl).pathname.length > 8
        ) {
            throw Error(`invalid short url: ${shortUrl}`);
        }
        const data = await Domains.findOne({ compressed: (<string>shortUrl).trim() });

        /**
         * 这个判断一般不需要
         * 因为服务端不知道请求端的交互场景，而且即使请求端不允许为空也可自行处理为非错误提示，比收到服务端错误交互更友好
         * 看业务场景需要
         */
        if (!data) {
            throw Error(`can not find domain info by ${shortUrl}.`);
        }
        return data;
    }
};
