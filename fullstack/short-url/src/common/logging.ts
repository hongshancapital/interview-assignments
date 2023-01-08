import winston from 'winston';

const { combine, timestamp, printf, colorize, align } = winston.format;

export const baseLoggerOptions = {
  level: process.env.LOG_LEVEL || 'info',
  transports: [new winston.transports.Console()],
  format: combine(
    colorize({ all: true }),
    timestamp({
      format: 'YYYY-MM-DD hh:mm:ss.SSS A',
    }),
    align(),
    printf((info) => `[${info.timestamp}] ${info.level}: ${info.message}`)
  ),
};

export const logger = winston.createLogger(baseLoggerOptions);

export const loggerOptions = {
  ...baseLoggerOptions,
  msg: '{{res.statusCode}} {{req.method}} {{res.responseTime}}ms {{req.url}}',
  colorize: true,
};
