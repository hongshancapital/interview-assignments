export class BaseConfig {
    public port = 3000;

    public shortUrlServer = 'https://x.cn';

    public mysqlConfig = {
        host: '127.0.0.1',
        port: 3306,
        username: 'username',
        password: 'pass',
        database: 'db',
    }
}

export const baseConfig = new BaseConfig();

// TODO 不同环境使用不同的配置, 集成自BaseConfig
