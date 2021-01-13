import config = require('config');
import { Connection, ConnectionOptions, createConnection } from 'typeorm';

const mysqlConfig = config.get<ConnectionOptions>('mysql');

let connection: Connection;

export const connectDb = async () => {
  try {
    connection = await createConnection(mysqlConfig);
  } catch (error) {
    throw new Error(error);
  }
};

export const getDbConnection = () => {
  return connection;
};
