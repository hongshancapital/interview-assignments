// Update with your config settings.
const path = require('path')
const yaml = require('yamljs')
const config = yaml.load(path.resolve(__dirname, `./config/${process.env.NODE_ENV || 'development'}/config.yml`))
const { postgres }  = config

module.exports = {
  development: {
    client: 'postgresql',
    connection: {
      ...postgres
    },
    pool: {
      min: 2,
      max: 10
    },
    migrations: {
      tableName: 'migrations'
    }
  },
  production: {
    client: 'postgresql',
    connection: {
      ...postgres
    },
    pool: {
      min: 2,
      max: 10
    },
    migrations: {
      tableName: 'migrations'
    }
  }
};
