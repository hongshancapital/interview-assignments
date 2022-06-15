import { RowDataPacket } from 'mysql2/promise';

import Database from '../infra/Database';

import Configuration from '../infra/Configuration';

/**
 * Short 持久化数据
 *
 * @public
 */
export interface ShortPO extends RowDataPacket {
  /**
   * 数据库序号
   *
   * @public
   */
  id: number;

  /**
   * 长链接
   *
   * @public
   */
  url: string;

  /**
   * 别名
   *
   * @public
   */
  alias: string;

  /**
   * 域名
   *
   * @public
   */
  domain: string;
}

export default class Short {

  /**
   * 生成随机字符串
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param size - 长度
   * @param id - 前缀
   *
   * @returns 返回混合后的值。
   */
  public static rand(size: number = 5, id: string = ''): string {
    const alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    let i = size;
    while (i--) {
      id += alphabet[(Math.random() * alphabet.length) | 0]
    }
    return id;
  }

  /**
   * 生成一个随机别名
   *
   * @remarks
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @returns 返回一个唯一别名。
   */
  public static async alias(length: number = 5): Promise<string> {
    let unique = false;

    const maxLoop = 20;

    let i = 0;

    if (length < 2) {
      length = 2;
    }

    let alias = '';

    while (!unique) {
      // 循环 20 次后, 长度 +1
      if (i >= maxLoop) {
        length++;
        i = 0;
      }

      alias = this.rand(length);

      const exist = await this.findOneShortByAlias(alias);

      // 如果不存在, 则代表是唯一值
      if (!exist) {
        unique = true;
      }

      i++;
    }

    return alias;
  }

  /**
   * 插入一条短链接数据
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param url - 长链接
   *
   * @returns 返回持久化对象
   */
  public static async insertShort(url: string) {
    const domain = Configuration.get('SHORT_DOMAIN', null);
    let exist = await this.findOneShortByURL(url);
    if (exist) return exist;
    const alias = await this.alias();
    const tableName = process.env.NODE_ENV === 'testing' ? "std_test_short_url" : 'std_url';
    const id = await Database.insert(`INSERT INTO ${tableName} (url, alias, domain) VALUES (:url, :alias, :domain)`, { url, alias, domain });
    return this.findOneShortByID(id);
  }

  /**
   * 根据别名检索短链接数据
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param alias - 别名
   *
   * @returns 返回持久化对象
   */
  public static async findOneShortByAlias(alias: string) {
    const tableName = process.env.NODE_ENV === 'testing' ? "std_test_short_url" : 'std_url';
    return Database.findOne<ShortPO>(`SELECT * FROM ${tableName} WHERE alias = :alias LIMIT  1`, { alias });
  }

  /**
   * 根据长链接检索短链接数据
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param url - 长链接
   *
   * @returns 返回持久化对象
   */
  public static findOneShortByURL(url: string) {
    const tableName = process.env.NODE_ENV === 'testing' ? "std_test_short_url" : 'std_url';
    return Database.findOne<ShortPO>(`SELECT * FROM ${tableName} WHERE url = :url LIMIT  1`, { url });
  }

  /**
   * 根据ID检索短链接数据
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param alias - 别名
   *
   * @returns 返回持久化对象
   */
  public static findOneShortByID(id: number) {
    const tableName = process.env.NODE_ENV === 'testing' ? "std_test_short_url" : 'std_url';
    return Database.findOne<ShortPO>(`SELECT * FROM ${tableName} WHERE id = :id LIMIT  1`, { id });
  }
}
