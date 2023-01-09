import mysql from "mysql"

import { DBPoolConfig } from "../model/conf"
import logger from "../lib/logger"

export class Database {
    private masterPool: mysql.Pool
    private slavePool: mysql.Pool

    constructor(masterConfig: DBPoolConfig, slaveConfig: DBPoolConfig) {
        this.masterPool = mysql.createPool(masterConfig)
        this.slavePool = mysql.createPool(slaveConfig)
    }

    // 获取对应连接池的连接
    public async getConnection(
        useMaster: boolean = false
    ): Promise<mysql.PoolConnection> {
        return new Promise((resolve, reject) => {
            const pool = useMaster ? this.masterPool : this.slavePool
            pool.getConnection((error, connection) => {
                if (error) {
                    logger.error(`DB pool getConnection error: ${error}`)
                    reject(error)
                } else {
                    resolve(connection)
                }
            })
        })
    }

    // 释放连接
    public releaseConnection(connection: mysql.PoolConnection): void {
        connection.release()
    }

    // 构建对应连接池的查询
    public async query(
        sql: string,
        params?: any,
        useMaster: boolean = false
    ): Promise<any> {
        const pool = useMaster ? this.masterPool : this.slavePool
        return new Promise((resolve, reject) => {
            pool.query(sql, params, (error, results) => {
                if (error) {
                    logger.error(
                        `DB ${
                            useMaster ? "master" : "slave"
                        } pool query error: ${error}`
                    )
                    reject(error)
                } else {
                    resolve(results)
                }
            })
        })
    }

    // 关闭连接池
    private async closePool(pool: mysql.Pool): Promise<mysql.MysqlError> {
        return new Promise((resolve) => pool.end(resolve))
    }

    public async close(): Promise<void> {
        return new Promise(async (resolve) => {
            await this.closePool(this.masterPool)
            await this.closePool(this.slavePool)
            resolve()
        })
    }
}
