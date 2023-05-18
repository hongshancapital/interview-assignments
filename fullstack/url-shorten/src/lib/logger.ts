/**
 * @description 日志
 */
import winston from "winston"
// import expressWinston from "express-winston"

const logger = winston.createLogger({
    level: process.env.NODE_ENV || "debug",
    format: winston.format.combine(
        winston.format.timestamp({ format: "YYYY-MM-DD HH:mm:ss" }),
        winston.format.printf(
            (info) => `${[info.timestamp]} ${info.level}: ${info.message}`
        )
    ),
    transports: [
        new winston.transports.Console({
            level: process.env.ERR_LOG_LEVEL || "debug",
        }),
        new winston.transports.File({
            filename: process.env.ERR_LOG_PATH || "./logs/error.log",
            level: process.env.ERR_LOG_LEVEL || "warn",
        }),
        new winston.transports.File({
            filename: process.env.ACCESS_LOG_PATH || "./logs/access.log",
            level: process.env.ACCESS_LOG_LEVEL || "debug",
        }),
    ],
})

export default logger
