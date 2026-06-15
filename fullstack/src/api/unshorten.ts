import { cache, db } from '../database';
import { validate } from '../validator/unshorten';
import logger from '../log/log';

async function unshorten(code: string): Promise<string> {
    return new Promise<string>((resolve, reject) => {
        validate(code)
        .then((code) => {
            return tryFetchOrigin(code)
        })
        .then((origin) => {
            if (origin) {
                resolve(origin);
            } else {
                reject({'status': 404, 'msg': `Short url: ${code} is not found.`});
            }
        })
        .catch((err) => {
            reject(err);
        });
    });
}

async function tryFetchOrigin(code: string): Promise<string | undefined> {
    return new Promise<string | undefined>((resolve, reject) => {
        cache.get(code)
        .then((origin) => {
            if (origin == undefined) {
                return tryGetFromDb(code);
            } else {
                return origin;
            }
        })
        .then((origin) => {
            resolve(origin);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

async function tryGetFromDb(code: string): Promise<string | undefined> {
    logger.debug(`try tryGetFromDb: ${code}`);
    return new Promise<string | undefined>((resolve, reject) => {
        db.get(code)
        .then((origin) => {
            if (origin) {
                cache.set(code, origin);
                resolve(origin);
            } else {
                cache.set(code, '');
                resolve(undefined);
            }
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export { tryFetchOrigin, tryGetFromDb };
export default unshorten;