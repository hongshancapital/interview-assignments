import mysql from "mysql";
import {encode, decode} from "./transform";
import * as dotenv from "dotenv";
import {createClient} from 'redis';

dotenv.config();

// 环境变量
export const env = <T = any>(key: string, defaultValue: T) => {
    return process.env[key] as T || defaultValue
}

// 数据库链接
const dbConnectionPool = mysql.createPool({
    connectionLimit: env("DB_CONNECTION_LIMIT", 10),
    host: env("DB_HOST", "localhost"),
    user: env("DB_USERNAME", "root"),
    port: env("DB_PORT", 3306),
    password: env("DB_PASSWORD", ""),
    database: env("DB_DATABASE", "test")
});

export const db = (execute: (connection: mysql.Connection) => void) => {
    dbConnectionPool.getConnection((err, connection) => {
        try {
            execute(connection)
        } finally {
            connection.release()
        }
    })
}

const redisClient = createClient({
    socket: {
        host: env("REDIS_HOST", "localhost"),
        port: env("REDIS_PORT", 6379)
    },
    password: env("REDIS_PASSWORD", ""),
    username: env("REDIS_USERNAME", ""),
    database: env("REDIS_DB", 0)
});

export const getCacheClient = async () => {
    if (!redisClient.isReady) {
        await redisClient.connect()
    }
    return redisClient
}

export const transform = {
    encode, decode
}


