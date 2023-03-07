const config = {
    /**
     * MySQL Database
     *
     * https://typeorm.io/#/connection-options
     */
    MYSQL_SETTING: {
        host: '10.7.20.3',
        port: 3306,
        username: 'username',
        password: process.env.DB_PWD!,
        database: 'short-url'
    },
    REDIS_URL: process.env.REDIS_URL ? `${process.env.REDIS_URL}` : 'redis://127.0.0.1:6379'
};

export type ConfigScheme = Readonly<Partial<typeof config>>;

export default config;
