import winston from 'winston';
/**
 * unified logger definition
*/
const options: winston.LoggerOptions = {
  transports: [
    new winston.transports.Console({
      level: process.env.NODE_ENV === 'production' ? 'info' : 'debug',
    }),
  ],
};

const winstonLogger = winston.createLogger(options);
type LogLevels = 'info' | 'debug' | 'error';

const logMessage = (level: LogLevels, code: string, message: unknown) => {
  if (typeof message === 'object') {
    try {
      message =
        message instanceof Error
          ? JSON.stringify(message, Object.getOwnPropertyNames(message))
          : JSON.stringify(message);
    } catch (e) {
      message = `logger could not stringify ${message}`;
    }
  }
  winstonLogger[level]({code, message});
};

class Logger {
  info(code: string, message: unknown) {
    logMessage('info', code, message);
  }
  debug(code: string, message: unknown) {
    logMessage('debug', code, message);
  }
  error(code: string, message: unknown) {
    logMessage('error', code, message);
  }
}

export const logger = new Logger();

