import { EggAppConfig, PowerPartial } from 'egg';
export default () => {
    const config: PowerPartial<EggAppConfig> = {};
    //sequelize
    config.sequelize = {
        logging: false,
        database: 'test',
        username: 'root',
        password: 'root',
        host: 'localhost',
        port: 3306,
        dialect: 'mysql',
        timezone: '+08:00',
        baseDir: 'model',
    };
    // the return config will combines to EggAppConfig
    return {
        ...config,
    };
}