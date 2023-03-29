import { Sequelize } from 'sequelize'
import config from '../config/config'

const sequelize = new Sequelize(config.MYSQL)

export default sequelize;