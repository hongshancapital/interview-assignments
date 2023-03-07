import mysql from 'mysql'


export const getShortLink = (shortLink: string) => mysql.format('SELECT * FROM link_map_table WHERE short_link = ?', [shortLink])

export const insertLongLink = (shortLink: string, longLink: string) => mysql.format('INSERT INTO link_map_table (short_link,long_link) VALUES (?, ?)',[shortLink, longLink])