import { Sequelize } from 'sequelize';
import dbConfig from '../config/db';
import { linkModel } from './link.model';

const { database, username, password, ...rest} = dbConfig;

const sequelize = new Sequelize(database || '', username || '', password, rest);

const db = {
  Sequelize,
  sequelize,
  link: linkModel(sequelize)
};

export default db;
