module.exports = {
  port: 3000,
  redis: {
    host: '10.31.90.17',
    port: 6379,
    db: 0,
    prefix: 'ShortUrl:',
  },
  mysql: {
    type: 'mysql',
    host: 'localhost',
    port: 3306,
    username: 'user',
    password: 'password',
    database: 'short-url',
    entities: ['dist/**/*.entity.js'],
    synchronize: true,
    logging: true,
  },
  cacheDuration: 3600,
};
