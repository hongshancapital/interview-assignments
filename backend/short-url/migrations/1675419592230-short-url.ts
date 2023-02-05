import { MigrationInterface, QueryRunner, Table, TableIndex } from "typeorm";


// "CREATE TABLE `shorturls` (`id` int NOT NULL, `short_url` varchar(8) NOT NULL, `long_url` varchar(4096) NOT NULL,`created_at` timestamp(32) NOT NULL,`updated_at` timestamp(32) NOT NULL, UNIQUE INDEX `IDX_short_url_long_url` (`short_url,long_url`), PRIMARY KEY (`id`)) ENGINE=InnoDB",

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
                type: 'timestamp',
                length: '32',
                comment: '创建时间',
              },
              {
                name: 'updated_at',
                type: 'timestamp',
                length: '32',
                comment: '更新时间',
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