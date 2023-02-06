const configs: {[key: string]: any} = {
  sequelize: {
    database: "short_url",
    host: 'localhost',
    // 使用内存数据库进行单元测试
    dialect: 'sqlite'
  }
}

export default configs;