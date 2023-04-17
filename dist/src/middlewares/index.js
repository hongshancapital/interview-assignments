"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const koa_compose_1 = __importDefault(require("koa-compose"));
const koa_logger_1 = __importDefault(require("koa-logger"));
const cors_1 = __importDefault(require("@koa/cors"));
const koa_bodyparser_1 = __importDefault(require("koa-bodyparser"));
const koa_static_1 = __importDefault(require("koa-static"));
const error_1 = __importDefault(require("./error"));
function middleware() {
    return koa_compose_1.default([
        koa_logger_1.default(),
        error_1.default(),
        koa_static_1.default('./src/views'),
        koa_static_1.default('./doc'),
        cors_1.default(),
        koa_bodyparser_1.default(),
    ]);
}
exports.default = middleware;
//# sourceMappingURL=index.js.map