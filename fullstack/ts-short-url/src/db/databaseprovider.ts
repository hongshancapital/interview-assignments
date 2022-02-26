import {Connection, createConnection} from 'typeorm';
import { ShortUrl } from '../models/shorturl';
import cfgs  from "../config/config"

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
            host: cfgs.db_host,
            port: cfgs.db_port,
            username: cfgs.db_username,
            password: cfgs.db_password,
            database: cfgs.db_name,                        
            ssl: cfgs.db_ssl
        }

        const { type, host, port, username, password, database, ssl } = DatabaseProvider.configuration;
        DatabaseProvider.connection = await createConnection({
            type, host, port, username, password, database,
            extra: {
                ssl
            },
            entities: [
                ShortUrl
            ],
            autoSchemaSync: true
        } as any); // as any to prevent complaining about the object does not fit to MongoConfiguration, which we won't use here

    }

    public static async UnitDB() {
        await DatabaseProvider.connection.close();
    }

    public static async getConnection(): Promise<Connection> {
        return DatabaseProvider.connection;
    }
}
