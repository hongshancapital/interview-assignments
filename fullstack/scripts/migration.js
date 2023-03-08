const knex = require('knex')
const path = require('path')
const yaml = require('yamljs')
const logger = require('../utils/log')

function migrateInit(config) {
  return () => {
    knex({
      client: 'postgresql',
      connection: {
        ...config
      }
    }).migrate.latest({
      directory: path.join(__dirname, '../migrations'),
      tableName: "migrations"
    }).then(([batchNo, log]) => {
      logger.info(`Migrate files: ${log.toString()}`)
      logger.info(`Batch ${batchNo} run: ${log.length} migrations`)
    }).catch(e => {
      if (e) logger.error(e)
    })
  }
}

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
    }).then(([batchNo, log]) => {
      logger.info('Already at the base migration')
      logger.info(`Rollback files: ${log.toString()}`)
      logger.info(`Batch ${batchNo} rolled back: ${log.length} migrations`)
    }).catch(e => {
      if (e) logger.error(e)
    })
  }
}

function migrateDown(config) {
  return () => {
    knex({
      client: 'postgresql',
      connection: {
        ...config
      }
    }).migrate.down({
      directory: path.join(__dirname, '../migrations'),
      tableName: "migrations"
    }).then(([batchNo, log]) => {
      logger.info(`Down files: ${log.toString()}`)
      logger.info(`Batch ${batchNo} down: ${log.length} migrations`)
    }).catch(e => {
      if (e) logger.error(e)
    })
  }
}

;(async () => {
  const [, , action = '', env = '', ...args] = process.argv
  const config = yaml.load(path.resolve(__dirname, `../config/${env || 'development'}/config.yml`))
  const {postgres} = config
  if (action === 'latest') {
    switch (env) {
      case 'production':
        migrateInit(postgres)()
        break
      case 'development':
        migrateInit(postgres)()
        break
      default:
        migrateInit(postgres)()
    }
  } else if (action === 'rollback') {
    switch (env) {
      case 'production':
        // 生产禁用
        console.log('disable')
        break
      case 'development':
        migrateRollBack(postgres)()
        break
      default:
        migrateRollBack(postgres)()
    }
  } else if (action === 'down') {
    switch (env) {
      case 'production':
        // 生产禁用
        console.log('disable')
        break
      case 'development':
        migrateDown(postgres)()
        break
      default:
        migrateDown(postgres)()
    }
  }
  setTimeout(() => {
    process.exit(0)
  }, 5000)
})()