"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.SHORT_DNS_MAX_RETRY = exports.SHORT_DNS_MAX_LENGTH = exports.SHORT_DNS_DOMAIN = exports.REDIS_URL = exports.SERVER_PORT = void 0;
var dotenv_1 = __importDefault(require("dotenv"));
var ENV = process.env.NODE_ENV || 'development';
// Load environment variables from .env file (.env.development, .env.test, .env.production)
dotenv_1.default.config({ path: ".env.".concat(ENV) });
var maxUintCheck = function (initValue, defaultValue, maxValue) {
    if (initValue === undefined) {
        return defaultValue;
    }
    var value = parseInt(initValue, 10);
    if (value < 0) {
        return defaultValue;
    }
    if (value > maxValue) {
        return maxValue;
    }
    return value;
};
// Server Port
exports.SERVER_PORT = process.env.SERVER_PORT || 8080;
// DB config
exports.REDIS_URL = process.env.REDIS_URL;
// Short DNS Domain
exports.SHORT_DNS_DOMAIN = process.env.SHORT_DNS_DOMAIN;
// Short DNS length, default 8, max 32
exports.SHORT_DNS_MAX_LENGTH = maxUintCheck(process.env.SHORT_DNS_MAX_LENGTH, 8, 32);
// Gen Short DNS max retry, default 5, max 10
exports.SHORT_DNS_MAX_RETRY = maxUintCheck(process.env.SHORT_DNS_MAX_RETRY, 5, 10);
