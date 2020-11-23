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
exports.TrackLink = void 0;
const typeorm_1 = require("typeorm");
let TrackLink = class TrackLink {
};
__decorate([
    typeorm_1.ObjectIdColumn(),
    __metadata("design:type", typeorm_1.ObjectID)
], TrackLink.prototype, "id", void 0);
__decorate([
    typeorm_1.Column(),
    __metadata("design:type", String)
], TrackLink.prototype, "shortened", void 0);
__decorate([
    typeorm_1.Column(),
    __metadata("design:type", String)
], TrackLink.prototype, "remoteAddress", void 0);
__decorate([
    typeorm_1.Column(),
    __metadata("design:type", String)
], TrackLink.prototype, "userAgent", void 0);
__decorate([
    typeorm_1.CreateDateColumn(),
    __metadata("design:type", String)
], TrackLink.prototype, "createdAt", void 0);
__decorate([
    typeorm_1.UpdateDateColumn(),
    __metadata("design:type", String)
], TrackLink.prototype, "updatedAt", void 0);
TrackLink = __decorate([
    typeorm_1.Entity()
], TrackLink);
exports.TrackLink = TrackLink;
//# sourceMappingURL=tracklink.js.map