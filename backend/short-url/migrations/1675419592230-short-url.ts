import { MigrationInterface, QueryRunner, Table, TableIndex } from "typeorm";

const TABLE_NAME = 'shorturls';
const ShortUrlIndex = 'IDX_short_url_long_url';
export class shortUrl1675419592230 implements MigrationInterface {
    async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.createTable(
          new Table({
            name: TABLE_NAME,
            columns: [
              {
                name: "id",
                type: "int",
                isPrimary: true
              },
              {
                name: "short_url",
                type: "varchar",
                length: '8',
              },
              {
                name: "long_url",
                type: "varchar",
                length: '4096',
              },
              {
                name: 'created_at',
                type: 'bigint',
                comment: '创建时间',
                default: Date.now(),
              },
              {
                name: 'updated_at',
                type: 'bigint',
                comment: '更新时间',
                default: Date.now(),
              },
            ]
          }),
          true
        );
    
        await queryRunner.createIndex(
            TABLE_NAME,
            new TableIndex({
              name: ShortUrlIndex,
              columnNames: ['short_url', 'long_url'],
              isUnique: true,
            }),
          );
    }
    
      async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.dropTable(TABLE_NAME);
      }

}