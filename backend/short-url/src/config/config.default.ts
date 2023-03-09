const config = {
    IS_DEV: true,
    PORT: 3000,
    /**
     * MySQL Database
     * https://typeorm.io/#/connection-options
     */
    MYSQL_SETTING: {
        host: 'localhost',
        port: 3306,
        username: 'dev',
        password: 'dev!123',
        database: 'short-url'
    },
    REDIS_URL: 'redis://:password@localhost:6379'
};

export type ConfigScheme = Readonly<Partial<typeof config>>;

export default config;
