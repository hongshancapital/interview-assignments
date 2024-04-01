import postgres from 'postgres';

export const sql = postgres({
  host: '127.0.0.1',
  port: 5432,
  username: 'postgres',
  types: {
    bigint: postgres.BigInt,
  },
});
