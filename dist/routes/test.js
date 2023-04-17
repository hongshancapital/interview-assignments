"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsonwebtoken_1 = __importDefault(require("jsonwebtoken"));
const config_1 = __importDefault(require("config"));
exports.default = (router) => {
    router
        .post('/test/token', async (ctx) => {
        const key = process.env.jwtSecret ?? config_1.default.get('jwtSecret');
        ctx.body = { token: jsonwebtoken_1.default.sign({ username: ctx.request.body.username }, key) };
    })
        .get('/test/auth', ctx => { ctx.body = `Welcome ${ctx.state.user.username}!`; })
        .get('/test/error', async () => { throw Error('Error handling works!'); })
        .get('/test/400', ctx => {
        ctx.status = 400;
        ctx.body = { status: { code: 1003, message: 'something went wrong' } };
    })
        .get('/test/301', ctx => { ctx.status = 301; });
};
//# sourceMappingURL=test.js.map