"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const winston_1 = require("winston");
const path_1 = __importDefault(require("path"));
const { combine, timestamp, json } = winston_1.format;
const logger = (0, winston_1.createLogger)({
    level: 'info',
    format: combine(timestamp(), json()),
    defaultMeta: { service: 'short_url_service' },
    transports: [
        new winston_1.transports.File({ filename: path_1.default.join(__dirname, '..', '../logs', 'error.log'), level: 'error' }),
        new winston_1.transports.File({ filename: path_1.default.join(__dirname, '..', '../logs', 'info.log') }),
    ],
});
exports.default = logger;
