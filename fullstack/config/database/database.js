const path = require('path')
const yaml = require('yamljs')
const config = yaml.load(path.resolve(__dirname, `../${process.env.NODE_ENV || 'development'}/config.yml`))
const { postgres }  = config
const knex = () => {
  return require('knex')({
    client: 'postgresql',
    connection: {
      ...postgres
    }
  });
}

module.exports = knex
