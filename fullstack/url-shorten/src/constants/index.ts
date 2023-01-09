// Redis 中存储的待转成 base62 的当前序号
export const CURRENT_SEQ_NUM = "CURRENT_SEQ_NUM"

// 每个提前发号段范围大小
export const RANGE = 50000

export const API = {
    SUCCESS_RETRIEVE: "The original long URL has been retrieved.",
    SUCCESS_SHORTEN: "The long URL has been successfully shortened.",
    ERROR: {
        LONG_URL_NOT_RETRIEVED:
            "The system was unable to retrieve the original long URL for this short URL. Please confirm that you have correctly shortened this URL before attempting to retrieve the original long URL.",
        INVALID_SHORT_URL: "The input short URL is not a valid URL.",
        INVALID_LONG_URL: "The input long URL is not a valid URL.",
        INVALID_HANDLE_SEQ:
            "An error has occurred. Please contact the system administrator for assistance.",
        INVALID_NUM_SEQ:
            "An error has occurred. Please contact the system administrator for assistance.",
    },
}

export const apiRateLimiter = {
    fetechUrl: {
        windowMs: 1 * 1000,
        max: 1000,
        standardHeaders: true,
        legacyHeaders: false,
    },
    shorten: {
        windowMs: 1 * 1000,
        max: 2000,
        standardHeaders: true,
        legacyHeaders: false,
    },
}
