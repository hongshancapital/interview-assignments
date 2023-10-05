import { Knex } from 'knex';

exports.seed = async (db: Knex): Promise<any> => {
  // Deletes ALL existing entries
  return db.from('short_url')
    .del()
    .then(
      async (): Promise<any> => {
        // Inserts seed entries
        const seedData = [
          {
            id: 1,
            app_id: 'testId',
            short_code: '4af638d4',
            origin_url: 'http://www.baidu.com/1',
            accessed_at: '2023-03-09'
          }
        ];
        return db.batchInsert('short_url', seedData, seedData.length);
      }
    );
};
