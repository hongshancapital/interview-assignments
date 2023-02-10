/**
 * 定义项目中的错误码
 * 其中 20X 表示成功
 */
export default {
    OK: 200,
    NOT_FOUND: 404,// 未找到资源
    SYS_ERR: 500,// 系统默认错误
    INVALID_URL: 300,// 非法的 url
    INVALID_CODE: 301,// 编码错误
    INVALID_PARAMS: 302,// 参数错误
    INVALID_NUMBER: 303,// 非法的编号
    INVALID_FLAG: 304,// 短链接 flag 不合法
}