/* eslint-disable no-param-reassign */
import * as winston from 'winston'

require('winston-daily-rotate-file')

const { format } = winston
const { combine, timestamp, printf } = format

const projectName = 'short-url'

class Logger {
  private projectName: string
  private level: string
  private winstonLogger: any

  constructor(projectName = 'demo', level = 'info') {
    this.projectName = projectName
    this.level = level
    this.init()
  }

  init() {
    const { projectName } = this
    const logFilePath = `./${projectName}`
    const formatter = combine(
      timestamp(),
      printf((info: any) => `[${new Date().toLocaleString()}] [${info.level}] ${info.message}`)
    )
    const { level } = this

    const transports = [
      // @ts-ignore
      new winston.transports.DailyRotateFile({
        format: formatter,
        filename: `${logFilePath}.%DATE%.log`,
        datePattern: 'YYYYMMDDHH',
        level: 'info',
      }),
    ]
    this.winstonLogger = winston.createLogger({
      level,
      transports,
    })
  }

  info(message: any) {
    this.winstonLogger.info(message)
  }

  error(message: any) {
    this.winstonLogger.error(`${message}`)
  }
}

export default new Logger(projectName, 'info')
