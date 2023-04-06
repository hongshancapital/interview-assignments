/**
 * 短域名长度最大为 8 个字符（不含域名）
 * ⚠️：应该可配置
 */
const SHORT_CODE_MAX_LENGTH = 8;

/**
 * 短域名前缀
 * ⚠️：应该可配置
 */
const SHORT_URL_PREFIX = 'https://scdt.com/';

/**
 * 长域名转换短域名参数
 */
interface IShortUrlParam {
    /**
     * 长域名信息
     */
    longUrl: string;
    /**
     * 短域名对应的唯一编码
     */
    shortCode?: string;
}

/**
 * 业务状态码
 */
enum StatusCode {
    /**
     * 业务处理失败
     */
    Error = -1,
    /**
     * 业务处理成功
     */
    Success = 0,
}
/**
 * 返回的业务结果包含信息
 */
interface IResult {
    /**
     * 业务处理状态码
     */
    code: StatusCode;
    /**
     * 业务处理结果文本提示
     */
    msg: string;
}

/**
 * 长域名转换短域名结果
 */
interface IShortUrlResult extends IResult {
    shortUrl: string;
}

export {
    SHORT_CODE_MAX_LENGTH,
    SHORT_URL_PREFIX,
    StatusCode,
    IShortUrlParam,
    IShortUrlResult,
};
