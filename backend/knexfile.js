module.exports = {
  development: {
    client: 'mysql2',
    connection: {
      host: 'localhost',
      database: 'demo',
      user: 'root',
      password: 'ht123456'
    },
    pool: {
      min: 2,
      max: 10
    }
  },
  test: {
    client: 'mysql2',
    connection: {
      /* connection info with database */
      host: 'localhost',
      user: 'root',
      password: 'ht123456'
    },
    migrations: {
      // tableName: 'short_url',
      directory: './migrations'
    },
    seeds: {
      directory: './src/db/seeds/test'
    }
  }
};
