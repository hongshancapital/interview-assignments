"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ERROR = exports.redisConfig = exports.CHARIN62 = void 0;
exports.CHARIN62 = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
exports.redisConfig = {
    host: 'sh-crs-a5v7i5ba.sql.tencentcdb.com',
    port: '27820',
    password: '33333333ll'
};
exports.ERROR = {
    INVALID_SHORTURL: {
        code: 'INVALID_SHORTURL',
        message: 'short url is invalid, please check'
    },
    INVALID_LONGURL: {
        code: 'INVALID_LONGURL',
        message: 'long url is invalid, please check'
    },
    SYS_ERR: {
        code: 'SYS_ERR',
        message: 'some system error'
    }
};
//# sourceMappingURL=constants.js.map