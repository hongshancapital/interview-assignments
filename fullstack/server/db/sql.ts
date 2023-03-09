import mysql from 'mysql'

/**
   * @description: mysql根据短链查找数据
   * @param {string} shortLink
   * @return {*}
*/
export const getShortLink: (shortLink: string) => string = (shortLink: string) => mysql.format('SELECT * FROM link_map_table WHERE short_link = ?', [encodeURIComponent(shortLink)])

/**
   * @description: 向mysql插入生成的长链/短链映射
   * @param {string} shortLink
   * @param {string} longLink
   * @return {*}
*/
export const insertLongLink:(shortLink: string, longLink: string) => string = (shortLink: string, longLink: string) => mysql.format('INSERT INTO link_map_table (short_link,long_link) VALUES (?, ?)',[encodeURIComponent(shortLink), encodeURIComponent(longLink)])