const Sequelize = require('sequelize');
const rp = require('request-promise');
//测试数据库配置
const dbConfig = {
    database: 'challenge', //数据库名
    account: 'root',      //用户名
    password: '123456',   //密码
    ip: 'localhost'       //数据库服务IP地址
} as const;

const sequelize = new Sequelize(dbConfig.database, dbConfig.account, dbConfig.password, {
    host: dbConfig.ip,
    dialect: 'mysql',
    logging: false,
    define: {
        freezeTableName: true
    }
});
const apiPrefix = 'http://127.0.0.1:3000/api/v1';
/**
 * 模拟http的get请求
 */
async function reqOfGet(url: string, data: object = {}): Promise<resqonseDataType> {
    let option = {
        method: 'get',
        url: `${apiPrefix}${url}`,
        json: true,
        qs: data
    };
    const rpBody = await rp(option);
    return rpBody;
}
/**
 * 根据长域名获取短域名
 */
async function getDataBylongName(longName: string) {
    return await sequelize.query(`select short_name from domain where long_name="${longName}";`);
}

/**
 * 清空域名数据
 */
async function clearDomain() {
    return await sequelize.query(`delete from domain;`);
}

/**
 * 创建域名数据
 */
async function createDomainName(longName: string, shortName: string) {
    return await sequelize.query(`insert into domain (id,long_name,short_name) values("${Sequelize.UUIDV4}","${longName}","${shortName}");`);
}
export {
    reqOfGet,
    getDataBylongName,
    clearDomain,
    createDomainName
};