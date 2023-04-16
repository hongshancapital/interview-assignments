import config from 'config'
import compose from 'koa-compose' // Compose the given middleware and return middleware
import logger from 'koa-logger'
import cors from '@koa/cors'
import bodyParser from 'koa-bodyparser'
import serve from 'koa-static'

import handleErrors from './error'
import auth from './auth'

export default function middleware () {
  return compose([
    logger(),
    handleErrors(),
    serve('./src/views'), // No authorization required for static resources
    serve('./doc'),
    cors(),
    // auth(),
    bodyParser(),
  ])
}
