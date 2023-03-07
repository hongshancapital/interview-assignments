import { DataSource } from 'typeorm';
import config from '../config';
export const dataSource = new DataSource({
    type: 'mysql',
    ...config.MYSQL_SETTING,
    entities: ['src/entity/*.ts'],
    logging: true // TODO: env
});

export async function loadDataSource() {
    return dataSource
        .initialize()
        .then(() => {
            console.log('Data Source has been initialized!');
        })
        .catch((err) => {
            console.error('Error during Data Source initialization:', err);
        });
}
