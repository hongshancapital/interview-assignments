"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const redis_1 = require("redis");
const md5_node_1 = __importDefault(require("md5-node"));
const number62_1 = __importDefault(require("../utils/number62"));
const config_1 = __importDefault(require("../config"));
const redisClientMap = {};
/**
 * 获取可写的存储对象
 * @returns 存储对象
 */
function getWriteRedisClient() {
    for (let key in redisClientMap) {
        const redisClient = redisClientMap[key];
        if (redisClient && redisClient.lastNum < config_1.default.db_size) {
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
function createId(redisClient) {
    return __awaiter(this, void 0, void 0, function* () {
        redisClient.lastNum++;
        yield redisClient.client.set('lastNum', redisClient.lastNum);
        return number62_1.default.encode(redisClient.lastNum, config_1.default.urlId_length);
    });
}
exports.default = {
    /**
     * 初始化所有存储节点
     * @param success 初始化成功回调函数
     * @param fail 初始化失败回调函数
     */
    init: function (success, fail) {
        const connPromiseArray = [];
        for (let key in config_1.default.redisurl_map) {
            let lastNum = 0;
            const client = (0, redis_1.createClient)({ url: config_1.default.redisurl_map[key] });
            redisClientMap[key] = { client, dbNo: key };
            client.on('error', (err) => console.log('Redis Client Error', err));
            connPromiseArray.push(client.connect().then(() => __awaiter(this, void 0, void 0, function* () {
                console.log(`redis[${key}] connected.`);
                const lastNumTmp = Number(yield client.get('lastNum'));
                if (lastNumTmp > 0) {
                    lastNum = lastNumTmp;
                }
                redisClientMap[key].lastNum = lastNum;
                console.log('Init redis completed', key);
            }), (reason) => {
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
    init_sync: function () {
        return __awaiter(this, void 0, void 0, function* () {
            const connPromiseArray = [];
            for (let key in config_1.default.redisurl_map) {
                let lastNum = 0;
                const client = (0, redis_1.createClient)({ url: config_1.default.redisurl_map[key] });
                redisClientMap[key] = { client, dbNo: key };
                client.on('error', (err) => console.log('Redis Client Error', err));
                connPromiseArray.push(client.connect().then(() => __awaiter(this, void 0, void 0, function* () {
                    console.log(`redis[${key}] connected.`);
                    const lastNumTmp = Number(yield client.get('lastNum'));
                    if (lastNumTmp > 0) {
                        lastNum = lastNumTmp;
                    }
                    redisClientMap[key].lastNum = lastNum;
                    console.log('Init redis completed', key);
                }), (reason) => {
                    console.error('Init redis failed', key, reason);
                }).then());
            }
            yield Promise.all(connPromiseArray);
        });
    },
    /**
     * 创建短域名
     * @param url 长域名
     * @returns  dbNo + urlId
     */
    createShortUrl: function (url) {
        return __awaiter(this, void 0, void 0, function* () {
            const redisClient = getWriteRedisClient();
            if (!redisClient) {
                throw new Error("No writeable db.");
            }
            const urlId = yield createId(redisClient);
            const hash16 = (0, md5_node_1.default)(url).substr(8, 16);
            yield redisClient.client.set('s:' + urlId, url);
            yield redisClient.client.set('k:' + hash16, urlId);
            return redisClient.dbNo + urlId;
        });
    },
    /**
     * 查询短域名
     * @param dbNo 存储编号（2位62进制，短域名2-3位）
     * @param urlId 短域名id（5位62进制，短域名4-8位）
     * @returns 长域名
     */
    queryShortUrl: function (dbNo, urlId) {
        return __awaiter(this, void 0, void 0, function* () {
            const redisClient = redisClientMap[dbNo];
            if (!redisClient) {
                return null;
            }
            return yield redisClient.client.get('s:' + urlId);
        });
    },
    /**
     * 查询长域名,找不到返回null
     * @param url 长域名
     * @returns dbNo + urlId
     */
    queryUrl: function (url) {
        return __awaiter(this, void 0, void 0, function* () {
            let urlId;
            let dbNo;
            const hash16 = (0, md5_node_1.default)(url).substr(8, 16);
            for (let key in redisClientMap) {
                const redisClient = redisClientMap[key];
                if (redisClient.lastNum > 0) {
                    urlId = yield redisClient.client.get('k:' + hash16);
                    if (urlId) {
                        dbNo = key;
                        break;
                    }
                }
            }
            return dbNo && urlId ? dbNo + urlId : null;
        });
    },
};
//# sourceMappingURL=shortUrl.js.map