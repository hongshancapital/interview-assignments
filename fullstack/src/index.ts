import express, { ErrorRequestHandler } from 'express'
import morgan from 'morgan'
import { Logger, MorganLoggerStream } from './utils/Logger'
import { ShortUrl } from './utils/ShortUrl'
import bodyParser from 'body-parser'
import { errors } from 'errorjs'
import "express-async-errors"

export async function createAppServer(configPath = process.env.CONFIG_FILE || '../config') {
  const config = require(configPath)
  const app = express()
  const shortUrl = new ShortUrl(config.redis)

  app.use(bodyParser.json())
  app.use(morgan('combined', { stream: MorganLoggerStream }))

  app.get('/:id', async (req, res) => {
    const url = await shortUrl.get(req.params.id)
    return res.redirect(url)
  })

  app.post('/', async (req, res) => {
    if (!req.body?.url || typeof req.body.url !== 'string' || !req.body.url.match(/^https?:\/\//)) throw new errors.ValidationError('url_check_failed')

    const id = await shortUrl.create(req.body.url)

    res.json({
      id,
      url: new URL(`/${id}`, config.url.base).toString()
    })
  })

  const errorHandler: ErrorRequestHandler = (err, _req, res, next) => {
    Logger.error(err)
    res.status(err.httpCode || 500).json({ code: err.code || 'internal_error' });
  }

  app.use(errorHandler);

  return {
    app,
    config,
  }
}

if (require.main === module) {
  createAppServer()
    .then(({ app, config }) => {
      const PORT = process.env.PORT || config.server.port || 3000
      const HOST = process.env.HOST || config.server.host || '127.0.0.1'

      app.listen(PORT, HOST, () => {
        Logger.info(`server listening on port http://${HOST}:${PORT}`)
      })
    })
    .catch((err) => {
      Logger.error(err)
    })
}