import "reflect-metadata";

import { createConnection, Connection } from "typeorm";
import { MysqlConnectionOptions } from "typeorm/driver/mysql/MysqlConnectionOptions";

export function connectDb(): Promise<Connection> {
    const options: MysqlConnectionOptions = {
        name: "default",
        type: "mysql",
        host: process.env.dbHost || "127.0.0.1",
        port: parseInt(process.env.dbPort!) || 3306,
        username: process.env.dbUser || "root",
        password: process.env.dbPassword ,
        database: process.env.dbName,
        entities: [
            __dirname + "/entity/*.ts",
            __dirname + "/entity/*.js",
        ],
        //synchronize: false,
        //logging: true
    };
    return createConnection(options);
}