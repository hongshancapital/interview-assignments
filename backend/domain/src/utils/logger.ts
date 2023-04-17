import { existsSync, mkdirSync } from "fs";
import { join } from "path";
import winston from "winston";
import winstonDaily from "winston-daily-rotate-file";
import configService from "../config/index";
import { createHook, executionAsyncResource } from 'async_hooks';

const logDir: string = join(__dirname, configService.get("log.dir") as string);

if (!existsSync(logDir)) {
  mkdirSync(logDir);
}

const logFormat = winston.format.printf(
  ({ timestamp, level, message }) => `${timestamp} ${level}: ${message}`
);

export const logger = winston.createLogger({
  format: winston.format.combine(
    winston.format.timestamp({
      format: "YYYY-MM-DD HH:mm:ss",
    }),
    logFormat
  ),
  transports: [
    // debug log setting
    new winstonDaily({
      level: "debug",
      datePattern: "YYYY-MM-DD",
      dirname: logDir + "/debug", // log file /logs/debug/*.log in save
      filename: `%DATE%.log`,
      maxFiles: 30, // 30 Days saved
      json: false,
      zippedArchive: true,
    }),
    // error log setting
    new winstonDaily({
      level: "error",
      datePattern: "YYYY-MM-DD",
      dirname: logDir + "/error", // log file /logs/error/*.log in save
      filename: `%DATE%.log`,
      maxFiles: 30, // 30 Days saved
      handleExceptions: true,
      json: false,
      zippedArchive: true,
    }),
  ],
});

logger.add(
  new winston.transports.Console({
    format: winston.format.combine(
      winston.format.splat(),
      winston.format.colorize()
    ),
  })
);

export const TRACE_ID = Symbol('traceId');
export interface TraceableInterface {
  [TRACE_ID]?: string;
}

export const tracerHook = createHook({
  init(_asyncId, _type, _triggerAsyncId, resource: TraceableInterface) {
    const currentResource: TraceableInterface = executionAsyncResource();
    if (currentResource?.[TRACE_ID]) {
      resource[TRACE_ID] = currentResource[TRACE_ID];
    }
  }
});

export function setupTraceId(traceId: string) {
  tracerHook.enable();
  const currentResource: TraceableInterface = executionAsyncResource();
  currentResource[TRACE_ID] = traceId;
  return currentResource[TRACE_ID];
}

export function getTraceId() {
  const currentResource: TraceableInterface = executionAsyncResource();
  return currentResource?.[TRACE_ID];
}
