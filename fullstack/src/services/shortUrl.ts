import { createClient } from 'redis';
import md5 from 'md5-node';
import number62 from '../utils/number62';
import config from '../config';

const redisClientMap = {};

/**
 * 获取可写的存储对象
 * @returns 存储对象
 */
function getWriteRedisClient() {
    for (let key in redisClientMap) {
        const redisClient = redisClientMap[key];
        if (redisClient && redisClient.lastNum < config.db_size) {
            return redisClient;
        }
    }
    return null;
}

/**
 * 创建新的urlId
 * @param redisClient 存储对象 
 * @returns urlId
 */
async function createId(redisClient: any): Promise<string> {
    redisClient.lastNum++;
    await redisClient.client.set('lastNum', redisClient.lastNum);
    return number62.encode(redisClient.lastNum, config.urlId_length);
}

export default {
    /**
     * 初始化所有存储节点
     * @param success 初始化成功回调函数
     * @param fail 初始化失败回调函数
     */
    init: function (success?: () => void, fail?: () => void) {
        const connPromiseArray = [];
        for (let key in config.redisurl_map) {
            let lastNum = 0;
            const client = createClient({ url: config.redisurl_map[key] });
            redisClientMap[key] = { client, dbNo: key };
            client.on('error', (err: any) => console.log('Redis Client Error', err));
            connPromiseArray.push(client.connect().then(async () => {
                console.log(`redis[${key}] connected.`);
                const lastNumTmp = Number(await client.get('lastNum'));
                if (lastNumTmp > 0) {
                    lastNum = lastNumTmp;
                }
                redisClientMap[key].lastNum = lastNum;
                console.log('Init redis completed', key);
                
            }, (reason: any) => {
                console.error('Init redis failed', key, reason);
            }).then());
        }
        Promise.all(connPromiseArray).then(res => {
            if (success) {
                console.log('Init all redis success', res);
                success();
            }
        }).catch((err) => {
            if (fail) {
                console.error('Init all redis failed', err);
                fail();
            }
        });
    },
    /**
     * 初始化所有存储节点(同步)
     */
    init_sync: async function () {
        const connPromiseArray = [];
        for (let key in config.redisurl_map) {
            let lastNum = 0;
            const client = createClient({ url: config.redisurl_map[key] });
            redisClientMap[key] = { client, dbNo: key };
            client.on('error', (err: any) => console.log('Redis Client Error', err));
            connPromiseArray.push(client.connect().then(async () => {
                console.log(`redis[${key}] connected.`);
                const lastNumTmp = Number(await client.get('lastNum'));
                if (lastNumTmp > 0) {
                    lastNum = lastNumTmp;
                }
                redisClientMap[key].lastNum = lastNum;
                console.log('Init redis completed', key);
                
            }, (reason: any) => {
                console.error('Init redis failed', key, reason);
            }).then());
        }
        await Promise.all(connPromiseArray);
    },
    /**
     * 创建短域名
     * @param url 长域名
     * @returns  dbNo + urlId
     */
    createShortUrl: async function (url: string): Promise<string> {
        const redisClient = getWriteRedisClient();
        if (!redisClient) {
            throw new Error("No writeable db.");
        }
        const urlId = await createId(redisClient);
        const hash16 = md5(url).substr(8, 16);
        await redisClient.client.set('s:' + urlId, url);
        await redisClient.client.set('k:' + hash16, urlId);
        return redisClient.dbNo + urlId;
    },
    /**
     * 查询短域名
     * @param dbNo 存储编号（2位62进制，短域名2-3位）
     * @param urlId 短域名id（5位62进制，短域名4-8位）
     * @returns 长域名
     */
    queryShortUrl: async function (dbNo: string, urlId: string): Promise<string> {
        const redisClient = redisClientMap[dbNo];
        if (!redisClient) {
            return null;
        }
        return await redisClient.client.get('s:' + urlId);
    },
    /**
     * 查询长域名,找不到返回null
     * @param url 长域名
     * @returns dbNo + urlId
     */
    queryUrl: async function (url: string): Promise<string> {
        let urlId: string;
        let dbNo: string;
        const hash16 = md5(url).substr(8, 16);

        for (let key in redisClientMap) {
            const redisClient = redisClientMap[key];
            if (redisClient.lastNum > 0) {
                urlId = await redisClient.client.get('k:' + hash16);
                if (urlId) {
                    dbNo = key;
                    break;
                }
            }
        }
        return dbNo && urlId ? dbNo + urlId : null;
    },
}