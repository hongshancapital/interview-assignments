import Knex from 'knex'
import 'dotenv/config'

interface ErrorResponse {
  code: number
  message: string
}

export const masterKnex = Knex({
  client: 'mysql',
  connection: {
    host: process.env.DB_HOST,
    port: Number(process.env.DB_PORT),
    database: process.env.DB_DATABASE,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    charset: 'utf8mb4',
  },
  debug: false,
  pool: {
    max: 10,
    min: 0,
    idleTimeoutMillis: 100,
    reapIntervalMillis: 150,
  },
  acquireConnectionTimeout: 60000,
})

export const isErrorResponse = (data): data is ErrorResponse => {
  if (!data) return true
  return (<ErrorResponse>data).code !== undefined
}

export const errorHandler = (err: {
  errno: number
  code: string
  stack?: string
}) => {
  const ret: ErrorResponse = {
    code: err.errno,
    message: err.code,
  }
  return ret
}
