const Sequelize = require('sequelize');
const domain = require('../model/domain.ts');
//数据库配置
const dbConfig = {
    database: 'challenge', //数据库名
    account: 'root',      //用户名
    password: '123456',   //密码
    ip: 'localhost'       //数据库服务IP地址
} as const;
const sequelize = new Sequelize(dbConfig.database, dbConfig.account, dbConfig.password, {
    host: dbConfig.ip,
    dialect: 'mysql',
    define: {
        freezeTableName: true
    }
});

try {
    sequelize.authenticate();
    console.log('Connection has been established successfully.');
} catch (error) {
    console.error('Unable to connect to the database:', error);
}
const Domain = sequelize.define('domain', domain, {
    timestamps: false
});
module.exports = {
    Domain
};
export { };