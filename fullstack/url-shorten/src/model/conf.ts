// DB 连接池配置
export interface DBPoolConfig {
    connectionLimit: number
    host: string
    user: string
    password: string
    database: string
}

// Redis 配置
export interface RedisConfig {
    auth?: boolean | false
    url?: string
    password?: string
    legacyMode?: boolean
}
