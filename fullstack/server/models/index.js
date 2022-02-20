const Sequelize = require('sequelize');
const { DB, USER, PASSWORD, ...rest} = require('../config/db.js');

const sequelize = new Sequelize(DB, USER, PASSWORD, rest);

const db = {};

db.Sequelize = Sequelize;
db.sequelize = sequelize;

db.link = require('./link.model.js')(sequelize, Sequelize);

module.exports = db;
