export const CODE = {
    // 成功
    SUCCESS: 0,
    ERROR: {
        // query参数缺失
        QUERY_ERROR: -100,
        // 系统异常
        SYSTEM_ERROR: -101,
        // 链接已失效
        URL_INVALID: -200
    }
}
export const MESSAGE = {
    SUCCESS: '成功',
    QUERY_ERROR: "参数错误!",
    SYSTEM_ERROR: '网络繁忙！',
    URL_INVALID: '链接已失效!'
}