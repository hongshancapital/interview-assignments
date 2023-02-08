type LogLevel = 'all' | 'mark' | 'trace' | 'debug' | 'info' | 'warn' | 'error' | 'fatal' | 'off'

interface EnvConfigType {
    NODE_ENV: 'development' | 'production' | 'test'
    SERVER_NAME: string
    PORT: string
    JWT_APP_NAME: string
    JWT_APP_ID: string
    JWT_APP_SECERT: string
    LOG_LEVEL?: LogLevel
    TRACE_LOG_LEVEL?: LogLevel
    DEV_LOG_LEVEL?: LogLevel
    AUDIT_LOG_LEVEL?: LogLevel
    REDIS_URL: string
    DB_URL: string
}
const developConfig: EnvConfigType = {
    NODE_ENV: 'development',
    SERVER_NAME: 'full stack test server',
    PORT: '3003',
    JWT_APP_NAME: 'server name',
    JWT_APP_ID: 'test-id',
    JWT_APP_SECERT: 'test-secret',
    LOG_LEVEL: 'all',
    TRACE_LOG_LEVEL: 'all',
    DEV_LOG_LEVEL: 'all',
    AUDIT_LOG_LEVEL: 'all',
    DB_URL: 'mongodb://localhost:27017/test',
    REDIS_URL: ''
};

export default <K extends keyof EnvConfigType>(env: K): EnvConfigType[K] => {
    if (!process.env.NODE_ENV || process.env.NODE_ENV === 'development') {
        return developConfig[env];
    }

    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    return process.env[env];
};
