import { DataSourceOptions } from "typeorm"
import { ShortUrlMap } from "./entity/ShortUrlMap"
import { password } from "./password"

export const databaseConfig: DataSourceOptions = {
	type: "mysql",
	host: "localhost",
	port: 3306,
	username: "",
	password: "",
	database: "short_url",
	synchronize: true,
	logging: false,
	entities: [ShortUrlMap],
	migrations: [],
	subscribers: [],
	...password,
}
