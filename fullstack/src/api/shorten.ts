import { generateHash, pickFirst, pickAll } from '../core/core';
import { db, cache } from '../database';
import { tryFetchOrigin } from './unshorten';
import { validate } from '../validator/shorten';
import logger from '../log/log';

async function shorten (url: string): Promise<string> {
    return new Promise<string>((resolve, reject) => {
        validate(url)
        .then((url) => {
            return shortenUrl(url);
        })
        .then((code) => {
            resolve(code);
        })
        .catch((err) => {
            reject(err);
        });
    });
};

async function shortenUrl(url: string): Promise<string> {
    const now: number = Date.now();
    const hash: string = generateHash(url);
    const first: string = pickFirst(hash);
    return new Promise<string>((resolve, reject) => {
        tryFetchOrigin(first)
        .then((origin) => {
            if (origin) {
                if (url == origin) {
                    return first;
                } else {
                    return undefined;
                }
            } else {
                return tryInsertDb(first, url, now);
            }
        })
        .then((code) => {
            if (!code) {
                return tryPickAnother(hash, url, now);
            } else {
                return code;
            }
        })
        .then((code) => {
            resolve(code);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

async function tryInsertDb(code: string, url: string, now: number): Promise<string | undefined> {
    logger.debug(`tryInsertDb: ${code}`);
    return new Promise<string | undefined>((resolve, reject) => {
        db.insert(code, url, now)
        .then((code) => {
            if (code) {
                cache.remove(code);
            }
            resolve(code);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

async function tryUpdateDb(code: string, url: string, now: number, lastUpdateTime: number): Promise<string | undefined> {
    return new Promise<string | undefined>((resolve, reject) => {
        db.update(code, url, now, lastUpdateTime)
        .then((code) => {
            if (code) {
                cache.remove(code);
            }
            resolve(code);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

async function tryPickAnother(hash: string, url: string, now: number): Promise<string> {
    const all: string[] = pickAll(hash);
    let infos: any[] = [];
    return new Promise<string>(async (resolve, reject) => {
        db.getMultiInfo(all)
        .then((results) => {
                infos = results;
                for (let info of infos) {
                    if (url == info['origin_url']) {
                        return info['short_url'];
                    }
                }
                return doPickAnother(url, now, all, infos);
        })
        .then((code) => {
            if (code) {
                resolve(code);
            } else {
                reject({'status': 500, 'msg': `Failed to shorten url: ${url}.`});
            }
        })
        .catch((err) => {
            reject(err);
        });
    });
}

async function doPickAnother(url: string, now: number, all: string[], infos: any[]): Promise<string | undefined> {
    let codes: string[] = infos.map((info) => info['short_url']);
    const set: Set<string> = new Set(codes);
    const left: any[] = all.filter((code) => !set.has(code)).map((code) => { 
        return { 'short_url': code, 'update_time': -1 };
     });
     infos.sort((a, b) => {
        return a['update_time'] - b['update_time'];
    });
    let candidates = left.concat(infos);
    return new Promise<string | undefined>(async (resolve, reject) => {
        try {
            for (const candidate of candidates) {
                let result: string|undefined;
                if (candidate['update_time'] == -1) {
                    result = await tryInsertDb(candidate['short_url'], url, now);
                } else {
                    result = await tryUpdateDb(candidate['short_url'], url, now, candidate['update_time']);
                }
                if (result) {
                    resolve(result);
                    return;
                }
            }
            resolve(undefined);
        } catch (err) {
            reject(err);
        }
    });
}

export { shortenUrl, tryInsertDb, tryUpdateDb, tryPickAnother, doPickAnother };
export default shorten;