import { createLogger, format, transports } from 'winston';
import { LOG_FILENAME, LOG_LEVEL } from './config';

export const logger = createLogger({
  transports: [
    new (transports.Console)({
      format: format.simple(),
      level: 'info',
      handleExceptions: true,
    }),
    new (transports.File)({
      filename: LOG_FILENAME,
      level: LOG_LEVEL,
      handleExceptions: true,
    }),
  ],
  exitOnError: false,
});

export class LoggerStream {
  write(message: string):void {
    logger.info(message);
  }
}
