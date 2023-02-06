export interface DbConfig {
    host: string;
    user: string;
    password: string;
    database: string;
}

/**
 * 数据库配置
 */
export const config: DbConfig = {
    host: process.env.DB_HOST!,
    user: process.env.DB_USER!,
    password: process.env.DB_PASSWORD!,
    database: process.env.DB_NAME!,
}
