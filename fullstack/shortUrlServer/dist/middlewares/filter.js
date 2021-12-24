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
Object.defineProperty(exports, "__esModule", { value: true });
exports.filterMiddleware = void 0;
const common_1 = require("@nestjs/common");
const utils_1 = require("../utils");
const bloomFilter_service_1 = require("../services/bloomFilter.service");
const constants_1 = require("../constants");
let filterMiddleware = class filterMiddleware {
    constructor(bloomFilterService) {
        this.bloomFilterService = bloomFilterService;
    }
    async use(req, res, next) {
        const method = req.method;
        const query = req.query;
        if ((method === 'get' || method === 'GET') && query) {
            const { shortUrl } = query;
            if (!shortUrl) {
                throw new Error(JSON.stringify(Object.assign(Object.assign({}, constants_1.ERROR.INVALID_SHORTURL), { message: 'please set the shortUrl value in url query' })));
            }
            else {
                console.log('shortUrl', shortUrl);
                const { hostname, pathnameValue } = utils_1.transformReqShortUrl(shortUrl);
                if (hostname !== req.hostname) {
                    throw new Error(JSON.stringify(Object.assign(Object.assign({}, constants_1.ERROR.INVALID_SHORTURL), { message: 'the shortUrl hostname is different from req hostname' })));
                }
                if (pathnameValue.length <= 1) {
                    throw new Error(JSON.stringify(Object.assign(Object.assign({}, constants_1.ERROR.INVALID_SHORTURL), { message: 'the shortUrl pathname length must more than 2' })));
                }
                console.log('pathnameValue', pathnameValue);
                const isMayExistInDb = await this.bloomFilterService.hasItemInBloomFilter(pathnameValue);
                console.log('isMayExistInDb', isMayExistInDb);
                if (isMayExistInDb) {
                    next();
                    return;
                }
                else {
                    throw new Error(JSON.stringify(Object.assign(Object.assign({}, constants_1.ERROR.INVALID_SHORTURL), { message: 'shortUrl is not exist in DB' })));
                }
            }
        }
        next();
    }
};
filterMiddleware = __decorate([
    common_1.Injectable(),
    __metadata("design:paramtypes", [bloomFilter_service_1.BloomFilterService])
], filterMiddleware);
exports.filterMiddleware = filterMiddleware;
//# sourceMappingURL=filter.js.map