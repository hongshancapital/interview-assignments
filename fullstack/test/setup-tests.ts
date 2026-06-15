import env from '../src/config/env'
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
//@ts-ignore
env.mongodbUrl = global.__MONGO_URI__
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
//@ts-ignore
env.mongodbName = global.__MONGO_DB_NAME__
