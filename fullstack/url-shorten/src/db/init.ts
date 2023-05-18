import { Database } from "../db/index"
import { DBPoolConfig } from "../model/conf"
import logger from "../lib/logger"

enum DBType {
    MASTER = "MASTER",
    SLAVE = "SLAVE",
}

function fetchDBConfig(type: DBType): DBPoolConfig {
    const prefix = type.toString()
    return {
        connectionLimit: parseInt(process.env[`${prefix}_LIMIT`] || "2000"),
        host: process.env[`${prefix}_HOST`] || "",
        user: process.env[`${prefix}_USER`] || "",
        password: process.env[`${prefix}_PWD`] || "",
        database: process.env[`${prefix}_DB_NAME`] || "",
    }
}

export default async function initDB(): Promise<Database> {
    try {
        const masterDBConfig = fetchDBConfig(DBType.MASTER)
        const slaveDBConfig = fetchDBConfig(DBType.SLAVE)
        return new Database(masterDBConfig, slaveDBConfig)
    } catch (error) {
        logger.error(`DB init error: ${error}`)
        throw error
    }
}
