import { isModuleNamespaceObject } from "util/types";

const redis = require('redis');
const client = redis.createClient(6379, '127.0.0.1');

client.on('ready', function () {
    console.log('Redis client: ready');
});

client.on('reconnecting', function () {
    console.log('redis reconnecting');
});

client.on('end', function () {
    console.log('Redis Closed!');
});

client.on('warning', function () {
    console.log('Redis client: warning');
});

client.on('error', function (err: string) {
    console.error('Redis Error ' + err);
});

const getString = (key: string) => {
    return new Promise((resolve, reject) => {
        client.get(key, function (err: Error, result: string) {
            if (err) {
                reject(err);
            }
            resolve(result);
        });
    });
};

const setString = (key: string, value: string, expire: number) => {
    return new Promise((resolve, reject) => {
        client.set(key, value, function (err: Error, result: string) {
            if (err) {
                reject(err);
            }

            if (!isNaN(expire) && expire > 0) {
                client.expire(key, String(expire));
            }
            resolve(result);
        })
    })
};

exports.long2ShortURL = (longURL: string) => {
    return getString(longURL);
}

exports.short2LongURL = (shortURL: string) => {
    return getString(shortURL);
}

exports.tryLock = (lockID: string) => {
    return new Promise((resolve, reject) => {
        client.sendCommand('setnx', lockID, '1', function (err: Error, result: string) {
            if (err) {
                reject(err);
            }

            client.expire(lockID, '3');
            resolve(result);
        });
    });
}

exports.freeLock = async (lockID: string) => {
    await client.del(lockID);
}

exports.setURL = async (shortURL: string, longURL: string) => {
    let multi = client.multi();

    multi.set(shortURL, longURL);
    multi.set(longURL, shortURL);

    await multi.exec();
}