import { DataSource } from "typeorm"
import { MysqlDB } from "@/data/datasource/mysql"

export const dataSource: DataSource = MysqlDB



export const setupDB = async () => {
    return dataSource.initialize()
}