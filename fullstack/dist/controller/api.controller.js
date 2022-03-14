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
exports.APIController = void 0;
const decorator_1 = require("@midwayjs/decorator");
const user_service_1 = require("../service/user.service");
const url_1 = require("../entity/url");
let APIController = class APIController {
    async getUser(aa) {
        console.log(aa);
        const user = await url_1.UrlModel.findAll();
        return { success: true, message: 'OK', data: user };
    }
};
__decorate([
    (0, decorator_1.Inject)(),
    __metadata("design:type", Object)
], APIController.prototype, "ctx", void 0);
__decorate([
    (0, decorator_1.Inject)(),
    __metadata("design:type", user_service_1.UserService)
], APIController.prototype, "userService", void 0);
__decorate([
    (0, decorator_1.Get)('/get'),
    __param(0, (0, decorator_1.Query)('uid')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", Promise)
], APIController.prototype, "getUser", null);
APIController = __decorate([
    (0, decorator_1.Controller)('/api')
], APIController);
exports.APIController = APIController;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYXBpLmNvbnRyb2xsZXIuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi8uLi9zcmMvY29udHJvbGxlci9hcGkuY29udHJvbGxlci50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7Ozs7QUFBQSxtREFBcUU7QUFFckUsMERBQXNEO0FBQ3RELHVDQUF5QztBQUd6QyxJQUFhLGFBQWEsR0FBMUIsTUFBYSxhQUFhO0lBUXhCLEtBQUssQ0FBQyxPQUFPLENBQWUsRUFBRTtRQUM1QixPQUFPLENBQUMsR0FBRyxDQUFDLEVBQUUsQ0FBQyxDQUFDO1FBQ2hCLE1BQU0sSUFBSSxHQUFHLE1BQU0sY0FBUSxDQUFDLE9BQU8sRUFBRSxDQUFDO1FBQ3RDLE9BQU8sRUFBRSxPQUFPLEVBQUUsSUFBSSxFQUFFLE9BQU8sRUFBRSxJQUFJLEVBQUUsSUFBSSxFQUFFLElBQUksRUFBRSxDQUFDO0lBQ3RELENBQUM7Q0FDRixDQUFBO0FBWEM7SUFEQyxJQUFBLGtCQUFNLEdBQUU7OzBDQUNJO0FBR2I7SUFEQyxJQUFBLGtCQUFNLEdBQUU7OEJBQ0ksMEJBQVc7a0RBQUM7QUFHekI7SUFEQyxJQUFBLGVBQUcsRUFBQyxNQUFNLENBQUM7SUFDRyxXQUFBLElBQUEsaUJBQUssRUFBQyxLQUFLLENBQUMsQ0FBQTs7Ozs0Q0FJMUI7QUFaVSxhQUFhO0lBRHpCLElBQUEsc0JBQVUsRUFBQyxNQUFNLENBQUM7R0FDTixhQUFhLENBYXpCO0FBYlksc0NBQWEifQ==