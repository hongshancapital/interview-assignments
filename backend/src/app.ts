import http from "http";
import Koa from 'koa';
import koaBody from "koa-body";
import { DataSource } from 'typeorm';
import { routes } from "./router";
import ShortUrlController from "./controller/short_url";
import { baseConfig } from './config';
import { UrlEntity } from './entity/Url';
import { IContext } from "./types/common";
import responseHandle from "./middleware/response_handle";
import accessLogger from "./middleware/access_logger";

export default class Application extends Koa {
    public port: number;
    public mysqlDataSource: DataSource;
    constructor() {
        super();
        this.port = baseConfig.port;
    }

    public async init () {
        this.initMiddleware();
        await this.initMysqlDatasource();
        this.initRoute();
    }

    // 给单元测试或者本地task用, mock一个context
    public createAnonymousContext (req: Koa.Request | any = null): IContext {
        // tslint:disable-next-line:no-any
        const request: Koa.Request | any = {
            headers: {
                'host': '127.0.0.1',
                'x-forwarded-for': '127.0.0.1',
            },
            query: {},
            querystring: '',
            host: '127.0.0.1',
            hostname: '127.0.0.1',
            protocol: 'http',
            secure: 'false',
            method: 'GET',
            url: '/',
            path: '/',
            socket: {
                remoteAddress: '127.0.0.1',
                remotePort: 7001,
            },
        };
        if (req) {
            for (const key in req) {
                if (key === 'headers' || key === 'query' || key === 'socket') {
                    Object.assign(request[key], req[key]);
                } else {
                    request[key] = req[key];
                }
            }
        }

        const response = new http.ServerResponse(request);
        return this.createContext(request, response) as IContext;
    }

    private async initMysqlDatasource () {
        const mysqlDatasource = new DataSource({
            type: 'mysql',
            host: baseConfig.mysqlConfig.host,
            port: baseConfig.mysqlConfig.port,
            username: baseConfig.mysqlConfig.username,
            password: baseConfig.mysqlConfig.password,
            database: baseConfig.mysqlConfig.database,
            synchronize: true,
            entities: [
                UrlEntity
            ]
        });

        await mysqlDatasource.initialize();

        this.mysqlDataSource = mysqlDatasource;
    }

    private initRoute () {
        const globalRouter = routes(
            [
                ShortUrlController
            ]
        )
        this.use(globalRouter.routes());
    }

    private initMiddleware () {
        this.use(koaBody());
        this.use(accessLogger);
        this.use(responseHandle);
    }
}
