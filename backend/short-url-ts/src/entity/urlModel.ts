import {Model, DataTypes, Sequelize, ModelCtor} from 'sequelize';

export let UrlModel: ModelCtor<UrlInstance>;

export interface UrlInstance extends Model {
    id: number;
    origin: string;
    is_deleted: boolean;
}

export function initModel(db: Sequelize): void {

    UrlModel = db.define<UrlInstance>('url', {
        id: {
            type: DataTypes.BIGINT,
            primaryKey: true,
            allowNull: false,
            autoIncrement: true
        },
        origin: {
            type: DataTypes.TEXT,
            allowNull: false,
            comment: '长链接'
        },
        is_deleted: {
            type: DataTypes.BOOLEAN,
            allowNull: false,
            comment: '是否已删除',
            defaultValue: false
        }
    }, {
        freezeTableName: true,
        timestamps: true,
        createdAt: false,
        updatedAt: 'ts'
    });
}