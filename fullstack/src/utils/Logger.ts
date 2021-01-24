import { createLogger, format, transports } from 'winston';

export const Logger = createLogger({
})

export const MorganLoggerStream = {
  write(text: string) {
    Logger.info(text)
  }
}

if (process.env.NODE_ENV !== 'production') {
  Logger.add(new transports.Console({
    format: format.combine(
      format.colorize(),
      format.simple(),
    ),
  }));
}