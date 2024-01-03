import { sql } from './db';
import { Base62, Encrypt } from './utils';

// CREATE TABLE short_links (
//   id bigserial PRIMARY KEY,
//   url text,
//   created_at timestamp DEFAULT current_timestamp
// );
// CREATE INDEX url_index ON short_links (url);

export const getKeyFromURL = async (url: string) => {
  const parsed = new URL(url);
  if (!['http:', 'https:'].includes(parsed.protocol)) {
    throw new Error('invalid url');
  }
  const id = await sql.begin(async (sql) => {
    const results =
      await sql`select id from short_links where url = ${url} limit 1`;
    if (results.length === 0) {
      return (
        await sql`insert into short_links (url) values (${url}) returning id`
      )[0].id;
    }
    return results[0].id;
  });
  return Base62.encode(Encrypt.encrypt(BigInt(id)));
};

export const getURLFromKey = async (key: string) => {
  const id = Encrypt.decrypt(Base62.decode(key));
  const results =
    await sql`select url from short_links where id = ${id} limit 1`;
  if (results.length === 0) {
    throw new Error('invalid url key');
  } else {
    const url = results[0].url;
    return url;
  }
};
