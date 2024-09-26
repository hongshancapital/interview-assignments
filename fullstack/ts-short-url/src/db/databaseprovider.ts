import {Connection, createConnection} from 'typeorm';
import { short_url } from '../models/shorturl';
import cfg  from "../config/config"

export interface DatabaseConfiguration {
    type: 'postgres' | 'mysql' | 'mssql';
    host: string;
    port: number;
    username: string;
    password: string;
    database: string;
    ssl?: boolean;
}

export class DatabaseProvider {
    private static connection: Connection;
    private static configuration: DatabaseConfiguration;

    public static async InitDB() {
        DatabaseProvider.configuration = {
            type: 'mysql',
            host: cfg.dbHost,
            port: cfg.dbPort,
            username: cfg.dbUserName,
            password: cfg.dbPassword,
            database: cfg.dbName,                        
            ssl: cfg.dbSSL
        }

        const { type, host, port, username, password, database, ssl } = DatabaseProvider.configuration;
        DatabaseProvider.connection = await createConnection({
            type, host, port, username, password, database,
            extra: {
                ssl
            },
            entities: [
                short_url
            ],
            autoSchemaSync: true
        } as any);
    }

    public static async UnitDB() {
        await DatabaseProvider.connection.close();
    }

    public static async getConnection(): Promise<Connection> {
        return DatabaseProvider.connection;
    }
}
