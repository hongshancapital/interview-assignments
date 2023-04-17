"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const koa_compose_1 = __importDefault(require("koa-compose"));
const koa_router_1 = __importDefault(require("koa-router"));
const shorturl_1 = __importDefault(require("./api/shorturl"));
const children = [
    { routes: shorturl_1.default, prefix: '/api' },
];
function routes() {
    const router = new koa_router_1.default();
    router
        .get('/api', (ctx) => {
        const map = new Map();
        router.stack.forEach(layer => {
            if (map.has(layer.path)) {
                const methods = map.get(layer.path);
                layer.methods.forEach(method => methods.add(method));
            }
            else {
                map.set(layer.path, new Set(layer.methods));
            }
        });
        ctx.body = [...map.entries()].map(([path, methods]) => `[${[...methods].join(' ')}] ${path}`);
    });
    children.forEach(child => {
        const nestedRouter = new koa_router_1.default();
        child.routes(nestedRouter);
        router.use(child.prefix, nestedRouter.routes(), nestedRouter.allowedMethods());
    });
    return koa_compose_1.default([router.routes(), router.allowedMethods()]);
}
exports.default = routes;
//# sourceMappingURL=index.js.map