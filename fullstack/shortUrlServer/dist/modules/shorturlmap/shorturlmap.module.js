"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.ShortUrlMapModule = void 0;
const common_1 = require("@nestjs/common");
const shorturlmap_service_1 = require("./shorturlmap.service");
const hash_service_1 = require("../../services/hash.service");
const redis_service_1 = require("../../services/redis.service");
const bloomFilter_service_1 = require("../../services/bloomFilter.service");
const shorturlmap_controller_1 = require("./shorturlmap.controller");
const typeorm_1 = require("@nestjs/typeorm");
const shorturlmap_entity_1 = require("./shorturlmap.entity");
let ShortUrlMapModule = class ShortUrlMapModule {
};
ShortUrlMapModule = __decorate([
    common_1.Module({
        imports: [
            typeorm_1.TypeOrmModule.forFeature([shorturlmap_entity_1.ShortUrlMap], 'primary'),
            typeorm_1.TypeOrmModule.forFeature([shorturlmap_entity_1.ShortUrlMap], 'backup')
        ],
        providers: [shorturlmap_service_1.ShortUrlMapService, hash_service_1.HashService, redis_service_1.RedisService, bloomFilter_service_1.BloomFilterService],
        controllers: [shorturlmap_controller_1.ShortUrlMapController],
    })
], ShortUrlMapModule);
exports.ShortUrlMapModule = ShortUrlMapModule;
//# sourceMappingURL=shorturlmap.module.js.map