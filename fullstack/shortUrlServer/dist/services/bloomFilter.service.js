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
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.BloomFilterService = void 0;
const common_1 = require("@nestjs/common");
const redis_service_1 = require("../services/redis.service");
const bloomfilter_redis_1 = __importDefault(require("bloomfilter-redis"));
let BloomFilterService = class BloomFilterService {
    constructor(redisService) {
        this.redisService = redisService;
        this.bloomFilter = new bloomfilter_redis_1.default({
            redisSize: 10000,
            hashesNum: 70,
            redisKey: 'Node_Bloomfilter_Redis',
            redisClient: this.redisService.client
        });
    }
    async addItemToBloomFilter(item) {
        await this.bloomFilter.add(item);
    }
    async hasItemInBloomFilter(item) {
        return this.bloomFilter.contains(item);
    }
};
BloomFilterService = __decorate([
    common_1.Injectable(),
    __metadata("design:paramtypes", [redis_service_1.RedisService])
], BloomFilterService);
exports.BloomFilterService = BloomFilterService;
//# sourceMappingURL=bloomFilter.service.js.map