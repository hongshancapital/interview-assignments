import { DataSource } from 'typeorm';
import { Record } from './record.entity';
import { TypeOrmModule } from '@nestjs/typeorm';

export const TypeOrmSQLITETestingModule = () => [
  TypeOrmModule.forRoot({
    type: 'better-sqlite3',
    database: 'demo.db',
    dropSchema: true,
    entities: [Record],
    synchronize: true,
  }),
  TypeOrmModule.forFeature([Record]),
];

export const testDatasetSeed = async () => {
  const dataSource = new DataSource({
    type: 'better-sqlite3',
    database: 'demo.db',
    dropSchema: true,
    entities: [Record],
    synchronize: true,
  });

  const connection = await dataSource.initialize();
  const entityManager = connection.createEntityManager();

  entityManager.insert(Record, {
    slug: 'abcdefgh',
    content: 'https://www.google.com',
  });
};
