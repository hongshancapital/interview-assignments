// 62 进制字符取值范围
export const CHARIN62 = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'

export const redisConfig = {
    host: process.env.REDIS_HOST,
    port: process.env.REDIS_PORT,
    password: process.env.REDIS_PASSWORD
}

export const ERROR = {
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
        message: 'some system error' // 暂未归类的系统异常
    }
}