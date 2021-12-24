"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.AppModule = void 0;
const common_1 = require("@nestjs/common");
const typeorm_1 = require("@nestjs/typeorm");
const shorturlmap_module_1 = require("./modules/shorturlmap/shorturlmap.module");
const filter_1 = require("./middlewares/filter");
const bloomFilter_service_1 = require("./services/bloomFilter.service");
const redis_service_1 = require("./services/redis.service");
const core_1 = require("@nestjs/core");
const exception_1 = require("./exception");
let AppModule = class AppModule {
    configure(consumer) {
        consumer.apply(filter_1.filterMiddleware).forRoutes('*');
    }
};
AppModule = __decorate([
    common_1.Module({
        imports: [
            typeorm_1.TypeOrmModule.forRoot({
                type: 'mysql',
                host: 'sh-cdb-lvon9ex8.sql.tencentcdb.com',
                port: 59246,
                username: 'root',
                password: '33333333ll',
                database: 'test',
                entities: [__dirname + '/**/*.entity{.ts,.js}'],
                synchronize: true,
                name: 'primary',
            }),
            typeorm_1.TypeOrmModule.forRoot({
                type: 'mysql',
                host: 'sh-cdb-4op54oiq.sql.tencentcdb.com',
                port: 59276,
                username: 'root',
                password: '33883386kk',
                database: 'test',
                entities: [__dirname + '/**/*.entity{.ts,.js}'],
                synchronize: true,
                name: 'backup',
            }),
            shorturlmap_module_1.ShortUrlMapModule
        ],
        providers: [
            bloomFilter_service_1.BloomFilterService,
            redis_service_1.RedisService,
            {
                provide: core_1.APP_FILTER,
                useClass: exception_1.AllExceptionsFilter
            }
        ]
    })
], AppModule);
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map