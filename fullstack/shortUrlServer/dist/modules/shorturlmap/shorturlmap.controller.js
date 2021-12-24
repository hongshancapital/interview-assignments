"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.ShortUrlMapController = void 0;
const common_1 = require("@nestjs/common");
const shorturlmap_service_1 = require("./shorturlmap.service");
const constants_1 = require("../../constants");
const hash_service_1 = require("../../services/hash.service");
const utils_1 = require("../../utils");
const redis_service_1 = require("../../services/redis.service");
const bloomFilter_service_1 = require("../../services/bloomFilter.service");
let ShortUrlMapController = class ShortUrlMapController {
    constructor(shortUrlService, hashServive, redisService, bloomFilterService) {
        this.shortUrlService = shortUrlService;
        this.hashServive = hashServive;
        this.redisService = redisService;
        this.bloomFilterService = bloomFilterService;
    }
    async getLongUrl(query = {}) {
        const { shortUrl } = query;
        if (!shortUrl) {
            throw new Error(JSON.stringify(Object.assign(Object.assign({}, constants_1.ERROR.INVALID_SHORTURL), { message: 'shortUrl not exist in url query!' })));
        }
        const { pathnameValue, hashFlag, realShortUrl } = utils_1.transformReqShortUrl(shortUrl);
        try {
            let longUrl = await this.redisService.get(pathnameValue);
            if (longUrl) {
                console.log('读redis 缓存');
                return {
                    data: longUrl
                };
            }
        }
        catch (e) {
            console.log('redis 读异常打印', e);
        }
        const id = this.shortUrlToId(realShortUrl);
        const urlMapRes = await this.shortUrlService.queryShortUrlMap({
            id,
            flag: hashFlag
        });
        console.log('urlMapRes', urlMapRes);
        if (urlMapRes.length === 0) {
            return {
                data: ''
            };
        }
        return {
            data: urlMapRes[0].longUrl
        };
    }
    async deleteShortUrlMap(body) {
        const { shortUrl } = body;
        if (!shortUrl) {
            throw new Error(JSON.stringify(Object.assign(Object.assign({}, constants_1.ERROR.INVALID_SHORTURL), { message: 'shortUrl not exist in url query' })));
        }
        const { pathnameValue, hashFlag, realShortUrl } = utils_1.transformReqShortUrl(shortUrl);
        const res = await this.shortUrlService.deleteShortUrlMap({
            shortUrl: realShortUrl,
            flag: hashFlag
        });
        console.log('res', res);
        try {
            await this.redisService.set(pathnameValue, '', 1);
        }
        catch (e) {
            console.log('redis 写异常打印', e);
        }
    }
    async createShortUrl(body, req) {
        console.log('body', body);
        const { longUrl, expireTime } = body;
        if (!longUrl) {
            throw new Error(JSON.stringify(Object.assign(Object.assign({}, constants_1.ERROR.INVALID_LONGURL), { message: 'longUrl not exist in req body!' })));
        }
        const hashFlag = this.hashServive.getEnvByHashUrl(longUrl);
        const existUrlMap = await this.shortUrlService.queryShortUrlMap({
            longUrl,
            flag: hashFlag
        });
        console.log('existUrlMap', existUrlMap);
        if (existUrlMap.length) {
            return {
                data: `http://${req.hostname}/${hashFlag}${existUrlMap[0].shortUrl}`
            };
        }
        const curTime = new Date();
        let finalExpireTime;
        if (expireTime) {
            finalExpireTime = new Date(expireTime);
        }
        else {
            finalExpireTime = new Date();
            finalExpireTime.setDate(finalExpireTime.getDate() + 1);
        }
        const insertId = await this.shortUrlService.createShortUrlMap({
            longUrl,
            flag: hashFlag,
            expireTime: finalExpireTime,
            createdTime: curTime
        });
        const realShortUrl = this.idToShortUrl(insertId);
        const pathnameValue = `${hashFlag}${realShortUrl}`;
        const shortUrl = `http://${req.hostname}/${pathnameValue}`;
        await this.shortUrlService.updateShortUrlMapById({
            id: insertId,
            updateData: {
                shortUrl: realShortUrl
            },
            flag: hashFlag
        });
        try {
            await this.redisService.set(pathnameValue, longUrl, Math.floor((finalExpireTime.getTime() - curTime.getTime()) / 1000));
        }
        catch (e) {
            console.log('redis 写异常打印', e);
        }
        try {
            this.bloomFilterService.addItemToBloomFilter(pathnameValue);
        }
        catch (e) {
            console.log('bloomFilter 添加元素报错', e);
        }
        return {
            data: shortUrl
        };
    }
    idToShortUrl(id) {
        let shorturl = [];
        while (id) {
            shorturl.unshift(constants_1.CHARIN62[id % 62]);
            id = Math.floor(id / 62);
        }
        return shorturl.join('');
    }
    shortUrlToId(shortUrl) {
        let id = 0;
        for (let i = 0; i < shortUrl.length; i++) {
            if ('a' <= shortUrl[i] && shortUrl[i] <= 'z')
                id = id * 62 + utils_1.calSingleCharSub(shortUrl[i], 'a');
            if ('A' <= shortUrl[i] && shortUrl[i] <= 'Z')
                id = id * 62 + utils_1.calSingleCharSub(shortUrl[i], 'A') + 26;
            if ('0' <= shortUrl[i] && shortUrl[i] <= '9')
                id = id * 62 + utils_1.calSingleCharSub(shortUrl[i], '0') + 52;
        }
        return id;
    }
};
__decorate([
    common_1.Get(),
    __param(0, common_1.Query()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", Promise)
], ShortUrlMapController.prototype, "getLongUrl", null);
__decorate([
    common_1.Delete(),
    __param(0, common_1.Body()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", Promise)
], ShortUrlMapController.prototype, "deleteShortUrlMap", null);
__decorate([
    common_1.Post(),
    __param(0, common_1.Body()), __param(1, common_1.Req()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object, Object]),
    __metadata("design:returntype", Promise)
], ShortUrlMapController.prototype, "createShortUrl", null);
ShortUrlMapController = __decorate([
    common_1.Controller('shortUrl'),
    __metadata("design:paramtypes", [shorturlmap_service_1.ShortUrlMapService,
        hash_service_1.HashService,
        redis_service_1.RedisService,
        bloomFilter_service_1.BloomFilterService])
], ShortUrlMapController);
exports.ShortUrlMapController = ShortUrlMapController;
//# sourceMappingURL=shorturlmap.controller.js.map