const koa = require('koa');
const router = require('./src/router/router.ts');
const koaBodyParser = require("koa-bodyparser");

const app = new koa();

app.use(async (ctx: any, next: any) => {
    await next()
    if (ctx.status === 404) {
        ctx.body = {
            "code": 404,
            "message": "调用接口url错误"
        };
    };
})
app.use(koaBodyParser());
app.use(router.routes());

app.listen("3000", () => {
    console.log("服务器启动");
});