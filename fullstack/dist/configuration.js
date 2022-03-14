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
exports.ContainerLifeCycle = void 0;
const decorator_1 = require("@midwayjs/decorator");
const koa = require("@midwayjs/koa");
const validate = require("@midwayjs/validate");
const info = require("@midwayjs/info");
const path_1 = require("path");
// import { DefaultErrorFilter } from './filter/default.filter';
// import { NotFoundFilter } from './filter/notfound.filter';
const report_middleware_1 = require("./middleware/report.middleware");
const sequlize = require("@midwayjs/sequelize");
let ContainerLifeCycle = class ContainerLifeCycle {
    async onReady() {
        // add middleware
        this.app.useMiddleware([report_middleware_1.ReportMiddleware]);
        // add filter
        // this.app.useFilter([NotFoundFilter, DefaultErrorFilter]);
    }
};
__decorate([
    (0, decorator_1.App)(),
    __metadata("design:type", Object)
], ContainerLifeCycle.prototype, "app", void 0);
ContainerLifeCycle = __decorate([
    (0, decorator_1.Configuration)({
        imports: [
            koa,
            validate,
            {
                component: info,
                enabledEnvironment: ['local'],
            },
            sequlize,
        ],
        importConfigs: [(0, path_1.join)(__dirname, './config')],
    })
], ContainerLifeCycle);
exports.ContainerLifeCycle = ContainerLifeCycle;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY29uZmlndXJhdGlvbi5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uL3NyYy9jb25maWd1cmF0aW9uLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7OztBQUFBLG1EQUF5RDtBQUN6RCxxQ0FBcUM7QUFDckMsK0NBQStDO0FBQy9DLHVDQUF1QztBQUN2QywrQkFBNEI7QUFDNUIsZ0VBQWdFO0FBQ2hFLDZEQUE2RDtBQUM3RCxzRUFBa0U7QUFDbEUsZ0RBQWdEO0FBY2hELElBQWEsa0JBQWtCLEdBQS9CLE1BQWEsa0JBQWtCO0lBSTdCLEtBQUssQ0FBQyxPQUFPO1FBQ1gsaUJBQWlCO1FBQ2pCLElBQUksQ0FBQyxHQUFHLENBQUMsYUFBYSxDQUFDLENBQUMsb0NBQWdCLENBQUMsQ0FBQyxDQUFDO1FBQzNDLGFBQWE7UUFDYiw0REFBNEQ7SUFDOUQsQ0FBQztDQUNGLENBQUE7QUFSQztJQURDLElBQUEsZUFBRyxHQUFFOzsrQ0FDZTtBQUZWLGtCQUFrQjtJQVo5QixJQUFBLHlCQUFhLEVBQUM7UUFDYixPQUFPLEVBQUU7WUFDUCxHQUFHO1lBQ0gsUUFBUTtZQUNSO2dCQUNFLFNBQVMsRUFBRSxJQUFJO2dCQUNmLGtCQUFrQixFQUFFLENBQUMsT0FBTyxDQUFDO2FBQzlCO1lBQ0QsUUFBUTtTQUNUO1FBQ0QsYUFBYSxFQUFFLENBQUMsSUFBQSxXQUFJLEVBQUMsU0FBUyxFQUFFLFVBQVUsQ0FBQyxDQUFDO0tBQzdDLENBQUM7R0FDVyxrQkFBa0IsQ0FVOUI7QUFWWSxnREFBa0IifQ==