import 'dotenv/config';
import { Sequelize } from 'sequelize';
const sequelize = new Sequelize(
  String(process.env.DB_DB),
  String(process.env.DB_USER),
  String(process.env.DB_PWD),
  {
    dialect: 'mysql',
    host: process.env.DB_HOST,
    pool: {
      max: 3,
    },
  }
);

const ready = async (): Promise<void> => {
  return sequelize
    .authenticate()
    .then(() => {
      console.log('mysql connected correctly');
    })
    .catch((err) => {
      console.error(err);
      process.exit(1);
    });
};
export { sequelize, ready };
