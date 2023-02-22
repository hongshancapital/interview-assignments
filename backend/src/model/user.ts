import { DataTypes } from 'sequelize';
import database from '../config/db';

const User = database.define('users', {
    name: {
        type: DataTypes.STRING
    }
}, { paranoid: true }) // Remove paranoid for hard deletion

export default User;