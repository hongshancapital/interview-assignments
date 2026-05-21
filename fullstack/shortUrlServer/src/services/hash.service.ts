import { Injectable } from '@nestjs/common';
import HashRing from 'hashring'

@Injectable()
export class HashService {
    private ring: HashRing
    constructor() {
        this.ring = new HashRing({ // 两台节点各50%
            '0': 50,
            '1': 50
        })
    }

    /**
     * 一致性hash 计算出当前长链请求对应哪个 DB
     * @param {string} longUrl
     * @returns {string}
     * @memberof HashService
     */
    getEnvByHashUrl(longUrl: string): string {
        return this.ring.get(longUrl);
    }
}
