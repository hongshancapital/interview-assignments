import { DataSource } from "typeorm";
import { ShortUrlInfo } from "./entity/shorturlinfo";

/*
 * datasource configuration
 * 1.为了保证项目可以运行，你需要保证数据库的配置正确，可以正常链接;
 */
const mysql_datasource = new DataSource({
    type: "mysql",
    host: "localhost",
    port: 3306,
    username: "root",
    password: "<your database password>",
    database: "test",
    entities: [
        ShortUrlInfo
    ],
    migrations: [
        "1674020275522-migration.ts"
    ]
})

mysql_datasource.initialize()
    .then(() => {
        console.log("INFO: mysql_datasource init succ")
    })
    .catch((error) => {
        console.log("ERR: mysql_datasource init error", error)
    })

export { mysql_datasource }
