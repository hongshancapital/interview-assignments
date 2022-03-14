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
exports.UrlModel = void 0;
const sequelize_typescript_1 = require("sequelize-typescript");
const sequelize_1 = require("@midwayjs/sequelize");
let UrlModel = class UrlModel extends sequelize_typescript_1.Model {
};
__decorate([
    (0, sequelize_typescript_1.Column)({
        comment: 'id',
    }),
    __metadata("design:type", Number)
], UrlModel.prototype, "name", void 0);
__decorate([
    (0, sequelize_typescript_1.Column)({
        comment: '短链接',
    }),
    __metadata("design:type", String)
], UrlModel.prototype, "shorturl", void 0);
__decorate([
    (0, sequelize_typescript_1.Column)({
        comment: '原始链接',
    }),
    __metadata("design:type", Number)
], UrlModel.prototype, "origin", void 0);
UrlModel = __decorate([
    sequelize_1.BaseTable
], UrlModel);
exports.UrlModel = UrlModel;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoidXJsLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vc3JjL2VudGl0eS91cmwudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7O0FBQUEsK0RBQXFEO0FBQ3JELG1EQUFnRDtBQUdoRCxJQUFhLFFBQVEsR0FBckIsTUFBYSxRQUFTLFNBQVEsNEJBQUs7Q0FlbEMsQ0FBQTtBQVhDO0lBSEMsSUFBQSw2QkFBTSxFQUFDO1FBQ04sT0FBTyxFQUFFLElBQUk7S0FDZCxDQUFDOztzQ0FDVztBQUtiO0lBSEMsSUFBQSw2QkFBTSxFQUFDO1FBQ04sT0FBTyxFQUFFLEtBQUs7S0FDZixDQUFDOzswQ0FDZTtBQUtqQjtJQUhDLElBQUEsNkJBQU0sRUFBQztRQUNOLE9BQU8sRUFBRSxNQUFNO0tBQ2hCLENBQUM7O3dDQUNhO0FBZEosUUFBUTtJQURwQixxQkFBUztHQUNHLFFBQVEsQ0FlcEI7QUFmWSw0QkFBUSJ9