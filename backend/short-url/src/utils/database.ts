import { DataSource } from 'typeorm';
import config from '@config';
export const dataSource = new DataSource({
    type: 'mysql',
    ...config.MYSQL_SETTING,
    entities: ['src/entities/*.ts'],
    synchronize: config.IS_DEV,
    logging: config.IS_DEV
});

export async function loadDB() {
    if (dataSource.isInitialized) return dataSource;
    return dataSource.initialize().catch((err) => {
        console.error('Error during Data Source initialization:', err);
    });
}

export async function closeDB() {
    return dataSource.destroy();
}
