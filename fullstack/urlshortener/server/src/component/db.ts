import * as path from 'path';
import { Connection, createConnection, EntityTarget, Repository } from 'typeorm';
import { DATABASE_URL, LOG_LEVEL } from './constant';

let connection: Connection;

export async function initializeDb(): Promise<void> {
  connection = await createConnection({
    type: 'postgres',
    synchronize: false,
    url: DATABASE_URL,
    logging: LOG_LEVEL === 'debug',
    cache: false,
    entities: [
      `${path.resolve(__dirname, '../entity')}/**.{js,ts}`
    ],
    migrations: [
      `${path.resolve(__dirname, '../migration')}/**.{js,ts}`
    ]
  });
  await connection.runMigrations();
}

export function getRepository<T>(entityClass: EntityTarget<T>): Repository<T> {
  return connection.getRepository(entityClass);
}
