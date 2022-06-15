import { expect } from 'chai';

import ShortService from '../src/services/short';
import Configuration from '../src/infra/Configuration';
import path from 'path';
import Database from '../src/infra/Database';
import { ResultSetHeader } from 'mysql2/promise';

describe('ShortService', () => {
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

  const tableName = process.env.NODE_ENV === 'testing' ? "std_test_short_url" : 'std_url';

  describe('rand', () => {
    it('should generate random string', () => {
      expect(ShortService.rand()).to.be.a('string');
    });

    it('should generate random not empty', () => {
      expect(ShortService.rand()).to.not.be.empty;
    });

    it('should generate random string length eq 5', () => {
      expect(ShortService.rand(5)).to.have.lengthOf(5);
    });
  });

  describe('alias', () => {
    it('should generate random string', async () => {
      await Database.exec<ResultSetHeader>(`DROP TABLE IF EXISTS ${tableName};`);

      await Database.exec(`
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

      const alias = await ShortService.alias();

      expect(alias).to.be.a('string')
      expect(alias).to.not.be.empty;
      expect(alias).to.have.lengthOf(5);
    });
  });

  describe('insertShort', () => {
    it('should generate random string', async () => {
      const po = await ShortService.insertShort("https://www.aa8i.com");
      expect(po.url).to.be.a('string').to.be.eql("https://www.aa8i.com");
    });
  });

  describe('findOneShortByAlias', () => {
    it('should findOne by alias', async () => {
      const po = await ShortService.findOneShortByAlias('baidu');
      expect(po.url).to.be.a('string').to.be.eql("https://www.baidu.com");
    });
  });

  describe('findOneShortByID', () => {
    it('should findOne by id', async () => {
      const po = await ShortService.findOneShortByID(1);
      expect(po.url).to.be.a('string').to.be.eql("https://www.baidu.com");
    });
  });

  describe('findOneShortByID', () => {
    it('should findOne by url', async () => {
      const po = await ShortService.findOneShortByURL("https://www.baidu.com");
      expect(po.url).to.be.a('string').to.be.eql("https://www.baidu.com");
    });
  });

});
