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
exports.HomeController = void 0;
const decorator_1 = require("@midwayjs/decorator");
let HomeController = class HomeController {
    async home() {
        return 'Hello Midwayjs!';
    }
};
__decorate([
    (0, decorator_1.Get)('/'),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", []),
    __metadata("design:returntype", Promise)
], HomeController.prototype, "home", null);
HomeController = __decorate([
    (0, decorator_1.Controller)('/')
], HomeController);
exports.HomeController = HomeController;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiaG9tZS5jb250cm9sbGVyLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vc3JjL2NvbnRyb2xsZXIvaG9tZS5jb250cm9sbGVyLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7OztBQUFBLG1EQUFzRDtBQUd0RCxJQUFhLGNBQWMsR0FBM0IsTUFBYSxjQUFjO0lBRXpCLEtBQUssQ0FBQyxJQUFJO1FBQ1IsT0FBTyxpQkFBaUIsQ0FBQztJQUMzQixDQUFDO0NBQ0YsQ0FBQTtBQUhDO0lBREMsSUFBQSxlQUFHLEVBQUMsR0FBRyxDQUFDOzs7OzBDQUdSO0FBSlUsY0FBYztJQUQxQixJQUFBLHNCQUFVLEVBQUMsR0FBRyxDQUFDO0dBQ0gsY0FBYyxDQUsxQjtBQUxZLHdDQUFjIn0=