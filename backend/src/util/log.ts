import env from "./validateEnv";

import winston from 'winston';
import DailyRotateFile from 'winston-daily-rotate-file';

// 配置 Winston 日志
export const logger = winston.createLogger({
    level: 'info', // 设置日志级别
    format: winston.format.combine(
        winston.format.timestamp(),
        winston.format.printf(({ timestamp, level, message }) => {
            return `${timestamp} [${level}]: ${message}`;
        })
    ),
    transports: [
        // 输出到控制台
        new winston.transports.Console(),

        // 每日日志轮换文件
        new DailyRotateFile({
            filename: `${env.LOG_PATH}/${'app-%DATE%.log'}`,
            datePattern: 'YYYY-MM-DD',
            zippedArchive: true,
            maxSize: '20m', // 每个日志文件的最大大小
            maxFiles: '7d', // 保留7天的日志文件
        }),

        // 每日日志轮换文件 - 错误日志
        new DailyRotateFile({
            filename: `${env.LOG_PATH}/${'app-error-%DATE%.log'}`,
            datePattern: 'YYYY-MM-DD',
            zippedArchive: true,
            maxSize: '20m', // 每个日志文件的最大大小
            maxFiles: '7d', // 保留7天的日志文件
            level: 'error', // 只记录 error 级别的日志
        }),
    ],
});