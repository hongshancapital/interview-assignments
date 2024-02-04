import { DataSource } from "typeorm";
import { ShortUriEntity } from "@/data/entities/ShortUriEntity";
import { isProd } from "@/constant";

export const MysqlDB = new DataSource({
    type: 'mysql',
    host: '127.0.0.1',
    username: 'root',
    password: 'root',
    database: 'short_url',
    entities: [ShortUriEntity],
    synchronize: !isProd
})