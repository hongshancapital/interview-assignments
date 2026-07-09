import { Model } from 'objection';
import Knex from 'knex';

// eslint-disable-next-line @typescript-eslint/no-var-requires
const knexConfig = require('../../knexfile');
// Database
const knex = Knex(knexConfig[process.env.NODE_ENV || 'development']);

// Connect database to Objection
Model.knex(knex);

export default knex;
