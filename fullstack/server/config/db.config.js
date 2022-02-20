module.exports = {
  USER: 'root',
  PASSWORD: 'abc123xyz',
  DB: 'link',
  dialect: 'mysql',
  host: 'localhost',
  pool: {
    max: 5,
    min: 0,
    acquire: 30000,
    idle: 10000
  },
  operatorsAliases: false
};
