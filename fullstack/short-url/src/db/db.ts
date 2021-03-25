'use strict';
import {shortUrlKey} from '../const';
/**
 * Database for local testing
 */
export default class ShortUrlDB {
  // store short key with origin URL
  static urlTable: any;

  /**
   * get origin URL
   * @param key base64 short key
   */
  static getOriginUrl(key: string): Promise<string> {
    return ShortUrlDB.urlTable.get(key);
  }

  /**
   * return the last integer key
   */
  static getCurrentId(): Promise<number> {
    return ShortUrlDB.urlTable.get(shortUrlKey);
  }

  /**
   * set the URL with short key
   * @param key base64 short key
   * @param value origin URL
   */
  static setUrl(key: string, value: any): Promise<boolean> {
    return ShortUrlDB.urlTable.put(key, value);
  }

  /**
   * set the last integer key
   * @param value the last integer key
   */
  static setId(value: number): Promise<boolean> {
    return ShortUrlDB.urlTable.put(shortUrlKey, value);
  }
}
