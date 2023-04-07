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
    /**
     * 短域名地址
     */
    shortUrl: string;
}

/**
 * 读取短域名对应的长域名信息
 */
interface IReadShortUrlResult extends IResult {
    /**
     * 长域名地址
     */
    longUrl: string;
}

/**
 * 根据参数创建短域名
 * @param param 创建短域名的参数
 * @returns 创建短域名后的返回值
 */
async function createShortUrl(param: IShortUrlParam): Promise<IShortUrlResult> {
    // 校验-参数较少，直接校验-不再引入校验工具
    // if(param.longUrl)
    return {} as IShortUrlResult;
}

export {
    SHORT_CODE_MAX_LENGTH,
    SHORT_URL_PREFIX,
    StatusCode,
    IShortUrlParam,
    IShortUrlResult,
    IReadShortUrlResult,
    createShortUrl,
};
