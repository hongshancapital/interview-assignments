import "reflect-metadata"
import { DataSource } from "typeorm"
import { Shorter } from "./entity/Shorter"

// ORM registe, make db's table easy to mana.
export const AppDataSource = new DataSource({
    type: "sqlite",
    database: "database.sqlite",
    synchronize: true,
    logging: false,
    entities: [Shorter],
    migrations: [],
    subscribers: [],
})
