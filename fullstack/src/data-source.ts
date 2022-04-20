import "reflect-metadata"
import { DataSource } from "typeorm"
import { ShortUrlMap } from "./entity/ShortUrlMap"

export const AppDataSource = new DataSource({
    type: "mysql",
    host: "localhost",
    port: 3306,
    username: "root",
    password: "qwe123123",
    database: "short_url",
    synchronize: true,
    logging: false,
    entities: [ShortUrlMap],
    migrations: [],
    subscribers: [],
})
