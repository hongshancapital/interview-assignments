const config = {
    IS_DEV: false,

    MYSQL_SETTING: {
        host: 'localhost',
        port: 3306,
        username: 'username',
        password: process.env.DB_PWD!,
        database: 'short-url'
    },
    REDIS_URL: process.env.REDIS_URL ? `${process.env.REDIS_URL}` : 'redis://127.0.0.1:6379'
};

export default config;
