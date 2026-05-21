import { expect } from 'chai';

import path from 'path';

import Configuration from '../src/infra/Configuration';

import Database from '../src/infra/Database';

import { createPool, PoolOptions, Pool, ResultSetHeader, RowDataPacket, OkPacket } from 'mysql2/promise';

import Short, { ShortPO } from '../src/services/short';

describe('Database', () => {
  Configuration.init({
    envFilePath: path.resolve(__dirname, '.env')
  });

  const pool = Database.init({
    host: Configuration.getOrThrow<string>('MYSQL_HOST'),
    port: Configuration.getOrThrow<number>('MYSQL_PORT'),
    user: Configuration.getOrThrow<string>('MYSQL_USER'),
    password: Configuration.getOrThrow<string>('MYSQL_PASSWORD'),
    database: Configuration.getOrThrow<string>('MYSQL_DATABASE'),
  });

  describe('init', () => {
    it('should init', () => {
      expect(pool).to.be.eql(Database.pool);
    });
  });

  describe('exec',  () => {
    const tableName = 'std_test_table';

    it('should SHOW DATABASES', async () => {
      expect(await Database.exec(`SHOW DATABASES;`)).to.be.an.instanceof(Array);
    });

    it('should CREATE TABLE', async () => {
      const [result] = await Database.exec<ResultSetHeader>(`
        CREATE TABLE IF NOT EXISTS ${tableName} (
          id INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID', 
          url TEXT NULL COMMENT '链接', 
          alias VARCHAR(50) NULL COMMENT '别名', 
          KEY alias (alias)
        ) COMMENT 'URL' ENGINE=InnoDB CHARSET=utf8mb4 AUTO_INCREMENT=0;
      `);

      expect(result.fieldCount).to.be.eql(0);
    });

    it('should INSERT ROWS', async () => {
      const [result] = await Database.exec<ResultSetHeader>(`
          INSERT INTO ${tableName} (url, alias)
          VALUES 
              ('https://www.baidu.com', 'baidu'),
              ('https://www.google.com', 'google');
      `);

      expect(result.fieldCount).to.be.eql(0);
      expect(result.affectedRows).to.be.eql(2);
    });

    it('should FIND ROWS', async () => {
      const [rows] = await Database.exec<ShortPO[]>(`
        SELECT *
        FROM ${tableName}
      `);

      expect(rows).to.have.lengthOf(2);
    });

    it('should FIND ONE ROWS', async () => {
      const [rows] = await Database.exec<ShortPO[]>(`
        SELECT *
        FROM ${tableName}
        WHERE id = 1;
      `);

      expect(rows[0].alias).to.be.eql('baidu');
      expect(rows[0].id).to.be.eql(1);
      expect(rows[0].url).to.be.eql("https://www.baidu.com");
    });

    it('should UPDATE ROWS', async () => {
      const [result] = await Database.exec<ResultSetHeader>(`
        UPDATE ${tableName}
        SET url   = 'https://www.google.com',
            alias = '${Short.rand()}'
        WHERE id = 1;
      `);

      expect(result.fieldCount).to.be.eql(0);
      expect(result.affectedRows).to.be.eql(1);
    });

    it('should DELETE ROWS', async () => {
      const [result] = await Database.exec<ResultSetHeader>(`
        DELETE
        FROM ${tableName}
        WHERE id = 2;
      `);

      expect(result.fieldCount).to.be.eql(0);
      expect(result.affectedRows).to.be.eql(1);
    });

    it('should DROP TABLE', async () => {
      const [result] = await Database.exec<ResultSetHeader>(`DROP TABLE IF EXISTS ${tableName};`);
      expect(result.fieldCount).to.be.eql(0);
    });

  });

  describe('findOne',  () => {
    const tableName = 'std_test_table1';

    it('should FIND ONE ROWS', async () => {
      await Database.exec<ResultSetHeader>(`
        CREATE TABLE IF NOT EXISTS ${tableName} (
          id INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID', 
          url TEXT NULL COMMENT '链接', 
          alias VARCHAR(50) NULL COMMENT '别名', 
          KEY alias (alias)
        ) COMMENT 'URL' ENGINE=InnoDB CHARSET=utf8mb4 AUTO_INCREMENT=0;
      `);

      await Database.exec<ResultSetHeader>(`
          INSERT INTO ${tableName} (url, alias)
          VALUES 
              ('https://www.baidu.com', 'baidu'),
              ('https://www.google.com', 'google');
      `);

      const row = await Database.findOne<ShortPO>(`
        SELECT *
        FROM ${tableName}
        WHERE id = 1;
      `);

      await Database.exec<ResultSetHeader>(`DROP TABLE IF EXISTS std_test_table1;`);

      expect(row.alias).to.be.eql('baidu');
      expect(row.id).to.be.eql(1);
      expect(row.url).to.be.eql("https://www.baidu.com");
    });
  });

  describe('find',  () => {
    const tableName = 'std_test_table2';
    it('should FIND ROWS', async () => {
      await Database.exec<ResultSetHeader>(`
        CREATE TABLE IF NOT EXISTS ${tableName} (
          id INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID', 
          url TEXT NULL COMMENT '链接', 
          alias VARCHAR(50) NULL COMMENT '别名', 
          KEY alias (alias)
        ) COMMENT 'URL' ENGINE=InnoDB CHARSET=utf8mb4 AUTO_INCREMENT=0;
      `);

      await Database.exec<ResultSetHeader>(`
          INSERT INTO ${tableName} (url, alias)
          VALUES 
              ('https://www.baidu.com', 'baidu'),
              ('https://www.google.com', 'google');
      `);

      const rows = await Database.find<ShortPO[]>(`
        SELECT *
        FROM ${tableName}
      `);

      await Database.exec<ResultSetHeader>(`DROP TABLE IF EXISTS ${tableName};`);

      const [first, last] = rows;

      expect(rows).to.have.lengthOf(2);

      expect(first.alias).to.be.eql('baidu');
      expect(first.id).to.be.eql(1);
      expect(first.url).to.be.eql("https://www.baidu.com");

      expect(last.alias).to.be.eql('google');
      expect(last.id).to.be.eql(2);
      expect(last.url).to.be.eql("https://www.google.com");
    });
  });

  describe('insert', () => {
    const tableName = 'std_test_table3';

    it('should INSERT ROWS', async () => {
      await Database.exec<ResultSetHeader>(`
        CREATE TABLE IF NOT EXISTS ${tableName} (
          id INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID', 
          url TEXT NULL COMMENT '链接', 
          alias VARCHAR(50) NULL COMMENT '别名', 
          KEY alias (alias)
        ) COMMENT 'URL' ENGINE=InnoDB CHARSET=utf8mb4 AUTO_INCREMENT=0;
      `);

      const id = await Database.insert(`
        INSERT 
          INTO
              ${tableName} (url, alias)
          VALUES 
            ('https://www.baidu.com', 'baidu'),
            ('https://www.google.com', 'google');
`     );

      await Database.exec<ResultSetHeader>(`DROP TABLE IF EXISTS ${tableName};`);

      expect(id).to.be.eql(1);
    });
  })
});