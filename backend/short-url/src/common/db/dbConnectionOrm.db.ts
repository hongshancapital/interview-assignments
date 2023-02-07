import { DataSource, DataSourceOptions } from 'typeorm';
import { ShortUrl } from '../../entities/shortUrl.entity';
import { config } from '../../common/config/index'
class DbConnection extends DataSource {
    private static instance: DbConnection;
    constructor(options: DataSourceOptions){
        super(options);
    }
    
    public static async getInstance(): Promise<DbConnection> {
        if (!DbConnection.instance) {
            const { type, host, port, database, username, password, synchronize } = config.dbConfig;
            DbConnection.instance = await new DbConnection({  
                type,
                host,
                port,
                database,
                username,
                password,
                synchronize,
                entities: [ShortUrl]
            }).initialize();
        }
        
        return DbConnection.instance;
    }
}

export { DbConnection };