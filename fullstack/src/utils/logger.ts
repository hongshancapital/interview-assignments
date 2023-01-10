import { createLogger, format, transports } from 'winston'
import path from 'path'

const { combine, timestamp, json } = format

const logger = createLogger({
  level: 'info',
  format: combine(
    timestamp(),
    json(),
  ),
  defaultMeta: { service: 'short_url_service' },
  transports: [
    new transports.File({ filename: path.join(__dirname, '..', '../logs', 'error.log'), level: 'error' }),
    new transports.File({ filename: path.join(__dirname, '..', '../logs', 'info.log') }),
  ],
})

export default logger
