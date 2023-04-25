// import MurmurHash3 from "imurmurhash";
import { hash } from './hash'


// const MAX32 = 4294967295;

//计算所需字节数
function optimalFilterSize(length: number, errorRate: number): number {
    return Math.ceil(-((length * Math.log(errorRate)) / Math.pow(Math.log(2), 2)))
}

//计算所需hash次数
function optimalHashes(size: number, length: number): number {
    return Math.ceil((size / length) * Math.log(2))
}


/**
 * 用Uint32Array来存储,每个bit代表一个元素的一位hash
 */
class BloomFilter {
    private _size: number;  //实际字节长度
    private _hashes: number;
    private _buckets: Uint32Array;
    public byteSize: number;    //Uint32Array长度

    constructor (size: number, fpp: number) {
        if (fpp >= 1) {
            throw new Error('fpp must be less than 1')
        }
        let optimalSize = optimalFilterSize(size, fpp);
        this.byteSize = Math.ceil(optimalSize / 32);    //计算Uint32Array长度
        this._size = this.byteSize * 32;            //计算实际长数
        this._hashes = optimalHashes(optimalSize, size);
        this._buckets = new Uint32Array(this.byteSize);
    }
    add(url: string): void {
        let l = this.getLocations(url);
        let bucket = this._buckets
        for (let i = 0; i < this._hashes; i++) {
            bucket[Math.floor(l[i] / 32)] |= 1 << (l[i] % 32)   //取商为Uint32Array的下标,取余为bit位置,与已有数字取或运算
        }
    }
    getLocations(url: string): Array<number> {
        let seed = Number.MAX_SAFE_INTEGER;
        let hash1 = hash(url);
        let hash2 = hash(url, seed);
        let locations = [];
        let idx = hash1 % this._size;   //计算实际字节位置
        for (let i = 0; i < this._hashes; i++) {
            locations[i] = idx
            idx = (idx + hash2) % this._size;   //计算实际字节位置
        }
        return locations
    }

    has(url: string): boolean {
        let locations = this.getLocations(url);
        let bucket = this._buckets
        for (let i = 0; i < this._hashes; i++) {
            let b = locations[i]
            if ((bucket[Math.floor(b / 32)] & (1 << (b % 32))) === 0) {
                return false
            }
        }
        return true;
    }
    getBuckets(): Uint32Array {
        return this._buckets
    }
}


export {
    optimalFilterSize,
    optimalHashes,
    BloomFilter
} 