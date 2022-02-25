import {Connection, createConnection} from 'typeorm';
import { shorturl } from '../models/shorturl';
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

    public static InitDB() {
        DatabaseProvider.configure({
            type: 'mysql',
            database: cfgs.db_name,
            username: cfgs.db_name,
            password: cfgs.db_password,
            host: cfgs.db_host,
            port: cfgs.db_port,
            ssl: cfgs.db_ssl
        });
    }

    private static configure(databaseConfiguration: DatabaseConfiguration): void {
        DatabaseProvider.configuration = databaseConfiguration;
    }

    public static async getConnection(): Promise<Connection> {
        if (DatabaseProvider.connection) {
            return DatabaseProvider.connection;
        }

        if (!DatabaseProvider.configuration) {
            throw new Error('DatabaseProvider is not configured yet.');
        }

        const { type, host, port, username, password, database, ssl } = DatabaseProvider.configuration;
        DatabaseProvider.connection = await createConnection({
            type, host, port, username, password, database,
            extra: {
                ssl
            },
            entities: [
                shorturl
            ],
            autoSchemaSync: true
        } as any); // as any to prevent complaining about the object does not fit to MongoConfiguration, which we won't use here

        return DatabaseProvider.connection;
    }
}
