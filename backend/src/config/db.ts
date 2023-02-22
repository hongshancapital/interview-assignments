import { Sequelize } from 'sequelize';

// Change this values to make it adjust to your case
// MySQL database name
const dbName = 'hs';
// Your username (default: root)
const user = 'root';
// Your password
const password = '1234567890';

const database = new Sequelize(dbName, user, password, {
    host: 'localhost',
    dialect: 'mysql',
    // logging: false
});

export default database;