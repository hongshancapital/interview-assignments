const config = {
    PORT: 4000,
    IS_DEV: false,
    MYSQL_SETTING: {
        host: 'localhost',
        port: 3306,
        username: 'root',
        password: 'yq6036',
        database: 'short-url'
    },
    REDIS_URL: 'redis://:yq6036@localhost:6379'
};

export default config;
