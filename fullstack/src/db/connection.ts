import { DataSource } from 'typeorm'

// 后续设置为环境变量，根据部署环境从config中取值
const myDataSource = new DataSource({
  type: 'mysql',
  host: 'test',
  port: 3306,
  username: 'test',
  password: 'test',
  database: 'test',
  entities: [ __dirname + '/../entity/*.{js,ts}' ],
  logging: true,
  synchronize: true,
})

// establish database connection
export const initDatabase = async () => myDataSource.initialize()

export default myDataSource
