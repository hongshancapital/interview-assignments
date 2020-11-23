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
exports.AppService = void 0;
const common_1 = require("@nestjs/common");
const typeorm_1 = require("typeorm");
const shorten_1 = require("./entity/shorten");
const tracklink_1 = require("./entity/tracklink");
const nanoid_1 = require("nanoid");
let AppService = class AppService {
    constructor() { }
    async shortenerLink(body) {
        let str_link = body.link;
        const entityManager = typeorm_1.getConnection().mongoManager;
        let existObj = await entityManager.findOne(shorten_1.Shorten, {
            link: str_link,
        });
        if (existObj != undefined) {
            return existObj;
        }
        else {
            let shortenObj = new shorten_1.Shorten();
            shortenObj.link = str_link;
            let new_shorten = await entityManager.save(shortenObj);
            new_shorten.shortened = nanoid_1.nanoid(8);
            return await entityManager.save(new_shorten);
        }
    }
    async retrieveShortened(id) {
        try {
            const entityManager = typeorm_1.getConnection().mongoManager;
            let shortenObj = await entityManager.findOne(shorten_1.Shorten, {
                shortened: id,
            });
            return shortenObj.link;
        }
        catch (error) {
            return 'Link no found';
        }
    }
    async trackShortened(id, req) {
        const entityManager = typeorm_1.getConnection().mongoManager;
        let shortenObj = await entityManager.findOne(shorten_1.Shorten, {
            shortened: id,
        });
        if (shortenObj != undefined) {
            let trackLink = new tracklink_1.TrackLink();
            trackLink.shortened = shortenObj.shortened;
            trackLink.remoteAddress =
                req.headers['x-forwarded-for'] || req.connection.remoteAddress;
            trackLink.userAgent = req.headers['user-agent'];
            await entityManager.save(trackLink);
        }
    }
};
AppService = __decorate([
    common_1.Injectable(),
    __metadata("design:paramtypes", [])
], AppService);
exports.AppService = AppService;
//# sourceMappingURL=app.service.js.map