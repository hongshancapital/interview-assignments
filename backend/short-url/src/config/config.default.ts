const config = {
    PORT: 3000,
    IS_DEV: true,
    /**
     * MySQL Database
     *
     * https://typeorm.io/#/connection-options
     */
    MYSQL_SETTING: {
        host: 'localhost',
        port: 3306,
        username: 'dev',
        password: 'dev!123',
        database: 'short-url'
    },
    REDIS_URL: 'redis://:redis@localhost:6379'
};

export type ConfigScheme = Readonly<Partial<typeof config>>;

export default config;
