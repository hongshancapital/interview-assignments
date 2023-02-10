import knex from 'knex'
import conf from '../config/db'

export default knex(({
    client: conf.driver,
    connection: conf.connection
}))