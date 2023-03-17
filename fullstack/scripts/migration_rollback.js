const knex = require('knex')
const path = require('path')
const yaml = require('yamljs')

function migrateRollBack(config) {
  return () => {
    knex({
      client: 'postgresql',
      connection: {
        ...config
      }
    }).migrate.rollback({
      directory: path.join(__dirname, '../migrations'),
      tableName: "migrations"
    })
  }
}

;(async () => {
  const [ ,, name = '', ...args ] = process.argv
  const config = yaml.load(path.resolve(__dirname, `../config/${name || 'development'}/config.yml`))
  const { postgres }  = config
  switch (name) {
    case 'production':
      // 禁用
      console.log('disable')
      break
    case 'development':
      migrateRollBack(postgres)()
      break
    default:
      migrateRollBack(postgres)()
  }
  setTimeout(() => {
    process.exit(0)
  }, 5000)
})()