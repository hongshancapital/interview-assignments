import { createClient, RedisClientType } from 'redis';

let reidsClient: RedisClientType;

const connectToRedis = async (): Promise<boolean>=> {
    if (!reidsClient?.isReady) {
        reidsClient = createClient();
        await reidsClient.connect();
    }
    return reidsClient.isReady;
}

const disconnectRedis = async () => {
    await reidsClient.disconnect();
}

const getDbSize = async (): Promise<number> => {
    return await reidsClient.sendCommand(['DBSIZE']);
}

const convert10To62 = (n: number) => {
    if (!Number.isInteger(n)) {
        return null;
    }
    if (n === 0) {
        return '0';
    }
    var digits = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var result = '';
    while (n > 0) {
        result = digits[n % digits.length] + result;
        n = parseInt(String(n / digits.length), 10);
    }
    if (result.length > 8) {
        return null;
    }
    return result;
}

const getShortUri = (host: string, key: string): string => {
    return `${host}/uri/${key}`;
}

const saveUri = (key: string, longUri: string): void => {
    reidsClient.set(key, longUri);
}

const getUri = async (key: string): Promise<string | null> => {
    return await reidsClient.get(key);
}

const isValidUri = (uri: string): boolean => {
    return /\w+:(\/?\/?)[^\s]+/gm.test(uri);
}

export {
    convert10To62,
    connectToRedis,
    disconnectRedis,
    getDbSize,
    getShortUri,
    saveUri,
    getUri,
    isValidUri
};