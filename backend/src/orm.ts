import { DataSource } from 'typeorm';
import { UrlMap }  from './entity/urlmap.entity'

export const dataSource = new DataSource({
    type: "mysql",
    host: "localhost",
    port: 3306,
    username: "test",
    password: "test",
    database: "test",
    entities: [UrlMap],
    synchronize: true,
    logging: false
})