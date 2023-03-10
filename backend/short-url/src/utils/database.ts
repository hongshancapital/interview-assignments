import { DataSource } from 'typeorm';
import config from '../config';
export const dataSource = new DataSource({
    type: 'mysql',
    ...config.MYSQL_SETTING,
    entities: ['src/entities/*.ts'],
    synchronize: config.IS_DEV,
    logging: config.IS_DEV
});

export async function loadDB() {
    return dataSource
        .initialize()
        .then(() => {
            console.log('Data Source has been initialized!');
        })
        .catch((err) => {
            console.error('Error during Data Source initialization:', err);
        });
}

export async function closeDB() {
    return dataSource.destroy().then(() => {
        console.log('Data Source has been closed!');
    });
}
