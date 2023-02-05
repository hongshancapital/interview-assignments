/* jshint indent: 2 */
// tslint:disable
import { DataTypes } from 'sequelize';
module.exports = function(app) {
    return app.model.define('url', {
        'id': {
            type: DataTypes.INTEGER.UNSIGNED,
            allowNull: false,
            primaryKey: true,
            autoIncrement: true,
            field: 'id'
        },
        //索引
        'shortId': {
            type: DataTypes.STRING(32),
            allowNull: false,
            defaultValue: '',
            field: 'short_id'
        },
        // 索引
        'url': {
            type: DataTypes.STRING(2000),
            allowNull: false,
            defaultValue: '',
            field: 'url'
        },
        'ip': {
            type: DataTypes.STRING(15),
            allowNull: false,
            defaultValue: '',
            field: 'ip'
        },
        'na': {
            type: DataTypes.STRING(128),
            allowNull: false,
            defaultValue: '',
            field: 'na'
        },
        'createdAt': {
            type: DataTypes.DATE,
            allowNull: true,
            field: 'created_at'
        },
        'updatedAt': {
            type: DataTypes.DATE,
            allowNull: true,
            field: 'updated_at'
        },
        'deletedAt': {
            type: DataTypes.DATE,
            allowNull: true,
            field: 'deleted_at'
        }
    }, {
        tableName: 'url',
        paranoid: true,
        createdAt: 'createdAt',
        updatedAt: 'updatedAt'
    });
};
