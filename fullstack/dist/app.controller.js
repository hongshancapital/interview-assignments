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
exports.AppController = void 0;
const common_1 = require("@nestjs/common");
const app_service_1 = require("./app.service");
const swagger_1 = require("@nestjs/swagger");
const ShortenDto_1 = require("./dto/ShortenDto");
let AppController = class AppController {
    constructor(appService) {
        this.appService = appService;
    }
    getShortened(shortenDto) {
        let originalUrl;
        try {
            originalUrl = new URL(shortenDto.link);
        }
        catch (err) {
            throw new common_1.HttpException({
                status: common_1.HttpStatus.BAD_REQUEST,
                error: 'invalid URL',
            }, common_1.HttpStatus.BAD_REQUEST);
        }
        return this.appService.shortenerLink(shortenDto);
    }
    getLink(params, req) {
        let shortenPromise = this.appService.retrieveShortened(params.id);
        this.appService.trackShortened(params.id, req);
        return shortenPromise;
    }
};
__decorate([
    common_1.Post('link'),
    __param(0, common_1.Body()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [ShortenDto_1.ShortenDto]),
    __metadata("design:returntype", void 0)
], AppController.prototype, "getShortened", null);
__decorate([
    common_1.Get('link/:id'),
    swagger_1.ApiParam({
        name: 'id',
    }),
    __param(0, common_1.Param()), __param(1, common_1.Req()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object, Object]),
    __metadata("design:returntype", void 0)
], AppController.prototype, "getLink", null);
AppController = __decorate([
    common_1.Controller('api'),
    __metadata("design:paramtypes", [app_service_1.AppService])
], AppController);
exports.AppController = AppController;
//# sourceMappingURL=app.controller.js.map