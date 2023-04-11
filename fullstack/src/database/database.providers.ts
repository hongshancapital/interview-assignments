import { DataSource } from 'typeorm';
export const databaseProviders = [
  {
    provide: 'DATA_SOURCE',
    useFactory: async () => {
      const dataSource = new DataSource({
        type: 'mysql',
        host: '192.168.2.15',
        port: 3306,
        username: 'coolcao',
        password: 'coolcao999',
        database: 'test',
        entities: [__dirname + '/../**/*.entity{.ts,.js}'],
        synchronize: false,
      });

      return dataSource.initialize();
    },
  },
];
