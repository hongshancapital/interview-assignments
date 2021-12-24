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
exports.ShortUrlMapService = void 0;
const common_1 = require("@nestjs/common");
const typeorm_1 = require("@nestjs/typeorm");
const typeorm_2 = require("typeorm");
const shorturlmap_entity_1 = require("./shorturlmap.entity");
let ShortUrlMapService = class ShortUrlMapService {
    constructor(primaryShortUrlMapRepository, backUpShortUrlMapRepository) {
        this.primaryShortUrlMapRepository = primaryShortUrlMapRepository;
        this.backUpShortUrlMapRepository = backUpShortUrlMapRepository;
    }
    async createShortUrlMap(params) {
        let { flag, longUrl = '', expireTime, createdTime } = params;
        const insertRes = await this.getDbByFlag(flag).insert({
            longUrl,
            createdTime,
            expireTime,
            shortUrl: ''
        });
        const id = insertRes.raw.insertId;
        return id;
    }
    async updateShortUrlMapById(params) {
        const { id, updateData, flag } = params;
        return this.getDbByFlag(flag).update({
            id
        }, Object.assign({}, updateData));
    }
    async queryShortUrlMap(params) {
        const { shortUrl, longUrl, id, flag } = params;
        let findParams = {};
        if (shortUrl) {
            findParams.shortUrl = shortUrl;
        }
        if (longUrl) {
            findParams.longUrl = longUrl;
        }
        if (id !== undefined) {
            findParams.id = id;
        }
        console.log('findParams', findParams);
        return this.getDbByFlag(flag).find(findParams);
    }
    async deleteShortUrlMap(params) {
        const { shortUrl, flag } = params;
        const reqParams = {};
        if (shortUrl) {
            reqParams.shortUrl = shortUrl;
        }
        return this.getDbByFlag(flag).delete(reqParams);
    }
    getDbByFlag(flag) {
        console.log('flag', flag);
        return flag === '1' ? this.backUpShortUrlMapRepository : this.primaryShortUrlMapRepository;
    }
};
ShortUrlMapService = __decorate([
    common_1.Injectable(),
    __param(0, typeorm_1.InjectRepository(shorturlmap_entity_1.ShortUrlMap, 'primary')),
    __param(1, typeorm_1.InjectRepository(shorturlmap_entity_1.ShortUrlMap, 'backup')),
    __metadata("design:paramtypes", [typeorm_2.Repository,
        typeorm_2.Repository])
], ShortUrlMapService);
exports.ShortUrlMapService = ShortUrlMapService;
//# sourceMappingURL=shorturlmap.service.js.map