"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const koa_jwt_1 = __importDefault(require("koa-jwt"));
const config_1 = __importDefault(require("config"));
exports.default = (opts) => {
    return koa_jwt_1.default({
        secret: process.env.jwtSecret ?? config_1.default.get('jwtSecret'),
        getToken: (ctx) => ctx.header.authorization
    }).unless({
        path: [
            /^\/[^/]*\/?$/,
            /test\/(?!auth)/
        ]
    });
};
//# sourceMappingURL=auth.js.map