import { createConnection, Connection } from 'mysql2'

import { createClient, RedisClientType } from 'redis';

export const redisClient: RedisClientType = createClient({
    url: process.env.REDIS_URL
});


export const mysqlConnection: Connection = createConnection({
    host: process.env.DB_HOST,
    port: process.env.DB_PORT as unknown as number,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME
})

export const connect = async () => {
    await redisClient.connect()
    await mysqlConnection.promise().connect()
}

export const close = async () => {
    await redisClient.disconnect()
    await mysqlConnection.promise().end()
}
