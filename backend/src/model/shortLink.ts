import {DataTypes, Sequelize} from 'sequelize';
import database from '../config/db';

const User = database.define('short_links', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        unique: true,
        autoIncrement: true
    },
    // 短网址
    shortUrl: {
        type: DataTypes.STRING,
        field: 'short_url',
    },
    // 原网址
    originalUrl: {
        type: DataTypes.STRING,
        field: 'original_url',
    },
    // 地址前缀（域名）
    domin: {
        type: DataTypes.STRING
    }
}, { paranoid: true, timestamps: false }) // Remove paranoid for hard deletion

export default User;