const config: { [key: string]: any } = {
  MYSQL: {
    dialect: 'mysql',
    database: 'short_db',
    username: 'root',
    password: '',
    host: 'localhost',
    port: 3306,
    timezone: '+08:00'
  },
  REDIS: {
    keyPrefix: '1short_url:'
  },
  BASE_HOST: 'http://localhost:3000/',
  REDIS_TTL: 30 * 24 * 3600,
}

export default config;
