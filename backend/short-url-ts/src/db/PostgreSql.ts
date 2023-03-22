import {Dialect, Sequelize} from 'sequelize';
import {dataBase as dbConfig} from '../config/config_datasource';
import {initModel} from "../entity/urlModel";
import * as Util from '../util';

export let db: Sequelize;

export async function connectMasterDb(): Promise<void> {
    let sequelize = new Sequelize(dbConfig.database, dbConfig.username, dbConfig.password, {
        host: dbConfig.host,
        port: dbConfig.port,
        dialect: dbConfig.type as Dialect,
        timezone: dbConfig.timezone,
        logging: (sqlString: string) => {
            if (dbConfig.logging) {
                console.log(sqlString);
            }
        },
        pool: {
            max: dbConfig.maxConnections,
            min: dbConfig.minConnections,
            idle: dbConfig.maxIdleTime
        },
        define: {
            timestamps: dbConfig.timestamps,
            freezeTableName: dbConfig.freezeTableName
        }
    });
    await sequelize.authenticate().catch((err: Error) => {
        console.error(`[PostgreSql]Unable to connect the database: ${err.message}`);
        Util.throwErr('500_pgsql_err', 'pgsql连接失败', 'ER230222002317');
    });
    console.log('[PostgreSql]DB connection has been established successfully.');
    db = sequelize;

    initModel(db);
    await sequelize.sync();
    console.log("[PostgreSql]table同步创建完成");
}

export async function closeDbConnection(): Promise<void> {
    await db.close();
    console.log('[PostgreSql]DB connection closed successfully.');
}