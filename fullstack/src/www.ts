import dotenv from 'dotenv';

// .evn 公共环境变量读取，优先执行
dotenv.config();

import express from 'express';
import api_router from './server/routes';
import bodyParser from 'body-parser';
import * as path from "path";
import * as fs from "fs";
import compression from "compression";

const app = express();
const PORT = 3000;
const HMR_PORT = 3001;
const isDev = process.env.NODE_ENV === 'development';




export async function createViteServer() {

    // api route 注册及中间件配置
    app.use(bodyParser.json());
    app.use(api_router);

    // SSR service 注册及处理包裹在vite启动逻辑中

    const vite = await (
        await import('vite')
    ).createServer({
        root: process.cwd(),
        logLevel: 'info',
        server: {
            middlewareMode: true,
            watch: {usePolling: true, interval: 100,},
            hmr: {port: HMR_PORT,},
        },
        appType: 'custom',
    });

    app.use(vite.middlewares); // vite production 静态资源、dev 静态资源 中间价
    if (!isDev) {

        console.log('production')
        app.use(compression());
        app.use(
            express.static(path.resolve(__dirname, 'client'))
        );
    }
    app.get("*", async (req, res, next) => {
        try {
            const url = req.originalUrl;
            let template = fs.readFileSync(isDev ?
                    path.resolve(__dirname, '../index.html') :
                    path.resolve(__dirname, "./client/index.html")
                , 'utf-8');
            template = await vite.transformIndexHtml(url, template);
            const {render} = await vite.ssrLoadModule(
                isDev ?
                    path.resolve(__dirname, "./client/entry-server.tsx") :
                    path.resolve(__dirname, "./client/server-bundle/entry-server.mjs")
            )
            const appHtml = render(url);
            const html = template.replace(`<!--app-html-->`, appHtml)

            // SSR 渲染 html string
            return res.status(200).set({'Content-Type': 'text/html'}).end(html);
        } catch (e) {
            isDev && vite.ssrFixStacktrace(e)
            next(e)
        }
    });

    return {app, vite}
}

createViteServer().then(({app}: any) =>  app.listen(PORT, () => {
    console.log('server start http://localhost:' + PORT)
}))


