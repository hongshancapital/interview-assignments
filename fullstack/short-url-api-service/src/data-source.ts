import "reflect-metadata"
import { DataSource } from "typeorm"
import { processEnv } from './app-config'
import { ShortUrl } from "./entity/ShortUrl"

export const AppDataSource = new DataSource({
    type: "mysql",
    host: processEnv.DB_HOST,
    port: (processEnv.DB_PORT as unknown) as number,
    username: processEnv.DB_USER,
    password: processEnv.DB_PASS,
    database:  processEnv.DB_NAME,
    synchronize: true,
    logging: false,
    entities: [ShortUrl],
    migrations: [],
    subscribers: [],
    // timezone: 'local',
})
