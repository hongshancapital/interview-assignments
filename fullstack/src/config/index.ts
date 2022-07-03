export const getDbConfig = (): any => ({
  type: 'postgres',
  host: process.env.POSTGRES_HOST,
  port: process.env.POSTGRES_PORT,
  username: process.env.POSTGRES_USER,
  password: process.env.POSTGRES_PASSWORD,
  database: process.env.POSTGRES_DB,
  logging: false,
  entities: [__dirname + '/../**/*.entity.{js,ts}'],
});

console.log('process.env.REDIS_HOST', process.env.POSTGRES_HOST);

export const getRedisConfig = (): any => ({
  readyLog: true,
  config: {
    host: process.env.REDIS_HOST,
    port: process.env.REDIS_PORT,
    password: process.env.REDIS_PASSWORD,
    readyLog: true,
  },
});
