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
exports.ShortUrlMapping = void 0;
const typeorm_1 = require("typeorm");
let ShortUrlMapping = class ShortUrlMapping {
};
__decorate([
    (0, typeorm_1.PrimaryGeneratedColumn)(),
    __metadata("design:type", Number)
], ShortUrlMapping.prototype, "id", void 0);
__decorate([
    (0, typeorm_1.Column)({
        name: 'short_url_code',
    }),
    __metadata("design:type", String)
], ShortUrlMapping.prototype, "shortUrlCode", void 0);
__decorate([
    (0, typeorm_1.Column)({
        name: 'original_url',
    }),
    __metadata("design:type", String)
], ShortUrlMapping.prototype, "originalUrl", void 0);
__decorate([
    (0, typeorm_1.Column)({
        name: 'original_url_hash',
    }),
    __metadata("design:type", String)
], ShortUrlMapping.prototype, "originalUrlHash", void 0);
__decorate([
    (0, typeorm_1.Column)({
        name: 'create_time',
    }),
    __metadata("design:type", Date
    // 预留字段
    )
], ShortUrlMapping.prototype, "createTime", void 0);
__decorate([
    (0, typeorm_1.Column)({
        name: 'create_user_id',
    }),
    __metadata("design:type", String)
], ShortUrlMapping.prototype, "createUserID", void 0);
ShortUrlMapping = __decorate([
    (0, typeorm_1.Entity)('short_url_mapping')
], ShortUrlMapping);
exports.ShortUrlMapping = ShortUrlMapping;
