import murmurhash from 'murmurhash';

const redis = require('./db/redis');
const mysql = require('./db/mysql');

const charSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

function int2Base62(id: number): string {
    let res: string = "";
    while (id > 0) {
        const r: number = id % 62;
        res = charSet[r] + res;
        id = Math.floor(id / 62);
    }
    return res;
}

export const generateShort = (longURL: string) => {
    return int2Base62(murmurhash.v3(longURL));
}

export const generateSalt = function () {
    return int2Base62(murmurhash.v3(String(Date.now())));
}

export const long2ShortURL = async (longURL: string) => {
    try {
        // redis: long -> short
        let short = await redis.long2ShortURL(longURL);
        // mysql: long -> short
        if (!short) {
            short = await mysql.long2ShortURL(longURL);
        }

        if (short) {
            return Promise.resolve(short);
        }
        // both not exist, insert and generate a new one!
        // get distributed lock
        let lock = await redis.tryLock(longURL);

        short = generateShort(longURL);
        // repeated short?
        let existedlongURL = await short2LongURL(short);
        if (existedlongURL) {
            short += generateSalt();
        }

        // write mysql
        await mysql.setURL(short, longURL);
        await redis.setURL(short, longURL);

        redis.freeLock(lock);
        return Promise.resolve(short || '');
    } catch(err) {
        return Promise.reject(err);
    }
};

export const short2LongURL = async (shortURL: string) => {
    try {
        // redis: short2Long
        let longURL = await redis.short2LongURL(shortURL);
        // mysql: long -> short
        if (!longURL) {
            longURL = await mysql.short2LongURL(longURL);
        }

        return longURL || '';
    } catch(err) {
        return Promise.reject(err);
    }
};