import { createHash } from 'crypto'
import { BASE62_CHARS, MAX_LENGTH_SHORT_ID } from '@/constant';


export function getHash(content: string) {
    return createHash('sha256').update(content).digest('hex')
}


export function convertBase10ToBase62(val: number) {
    const scale = 62;
    let _val = val
    let res = ''
    while (_val >= scale) {
        const remainder = _val % scale
        const valHex62 = BASE62_CHARS.charAt(remainder)
        res = valHex62 + res;
        _val = Math.floor(_val / scale);
    }
    if (_val !== 0) {
        res = BASE62_CHARS.charAt(_val) + res
    }
    return res
}

export class StringEncoder {
    private content;
    private _hash;
    private index;
    private instance;
    constructor(content: string) {
        this.content = content;
        this._hash = getHash(this.content)
        this.index = 0;
        this.instance = this;
       
    }

    get hash() {
        return this._hash
    }

    generateId() {
        if (!this || !this.instance) {
            throw new TypeError('Illegal invocation')
        }
        const maxHexLen = 11;
        let hex = this.hash.substring(this.index, this.index + maxHexLen);

        if (hex.length < 11) {
            const padEndStr = this.hash.substring(0, maxHexLen - hex.length)
            hex = `${hex}${padEndStr}`;
        }

        const decimalNum = parseInt(`0x${hex}`, 16);
        let id = convertBase10ToBase62(decimalNum);

        if (id.length < MAX_LENGTH_SHORT_ID) {
            id = id.padStart(MAX_LENGTH_SHORT_ID, '0')
        }
        if(id.length > MAX_LENGTH_SHORT_ID){
            id = id.substring(0, MAX_LENGTH_SHORT_ID);
        }

        this.index = this.index === this.hash.length - 1 ? 0 : this.index + 1;
        return id
    }
}
