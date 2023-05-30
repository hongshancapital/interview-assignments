import Koa, { Middleware, ParameterizedContext } from "koa";
import { DataSource } from "typeorm";

// Application
export interface Application extends Koa {
    mysqlDataSource: DataSource;
}

export interface IContext extends ParameterizedContext {
    app: Application;
}

// router
interface MethodParams {
    path: string;
    middlewares: Middleware[];
    httpMethod: 'GET' | 'POST' | 'PUT' | 'DELETE';
}

// error
export interface ICustomError {
    message?: string;
    code: string;
    statusCode: number;
    errorCode: number;
    desc: string;
}
