const koaRouter = require('koa-router');

const apiServer = require('../server/apiServer.ts');

const apiRouter = new koaRouter({ prefix: '/api/v1' });

apiRouter.get('/domainName/short', async (ctx: any, next: any) => {
    let result = await apiServer.storeShortDomainName(ctx.request.query.longName);
    ctx.body = result;
});
apiRouter.get('/domainName/long', async (ctx: any, next: any) => {
    let result = await apiServer.getLongDomainName(ctx.request.query.shortName);
    ctx.body = result;
});
module.exports = apiRouter;