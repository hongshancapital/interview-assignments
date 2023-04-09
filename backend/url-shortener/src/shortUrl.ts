import { isWebUri } from 'valid-url';
import config from 'config';
import { db, SHORT_URL_TABLE } from './db';
import { ShortUrlError } from './ShortUrlError';
import { nanoid } from 'nanoid';
import { cache } from './cache';
/**
 * 短码长度
 */
const SHORT_CODE_MAX_LENGTH = config.get<number>('shortCodeMaxLength');

/**
 * 短域名前缀
 */
const SHORT_URL_PREFIX = config.get<string>('shortUrlPrefix');

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
 * shortUrls 表数据体
 */
interface IShortUrl {
    shortCode: string;
    longUrl: string;
}

class ShortUrl {
    private data: IShortUrl;
    /**
     * 重试次数
     */
    private retryNum: number;
    constructor(data: IShortUrl, retryNum = 3) {
        this.data = data;
        this.retryNum = retryNum;
    }

    async findByLongUrl(): Promise<IShortUrl | undefined> {
        return db<IShortUrl>(SHORT_URL_TABLE)
            .where('longUrl', this.data.longUrl)
            .first();
    }
    async findByShortCode(): Promise<IShortUrl | undefined> {
        return db<IShortUrl>(SHORT_URL_TABLE)
            .where('shortCode', this.data.shortCode)
            .first();
    }

    async insert(): Promise<string> {
        try {
            await db(SHORT_URL_TABLE).insert(this.data);
        } catch (e) {
            // 在此处通过数据库约束，减少提前查询次数
            // 判断是否是长域名已存在导致的错误
            const result1 = await this.findByLongUrl();
            if (result1) {
                // 返回已有的短码
                return result1.shortCode;
            }
            // 判断是否是短码已存在导致的错误
            const result2 = await this.findByShortCode();
            if (result2 && this.retryNum > 0) {
                // 重新生成短码进行存储
                this.data.shortCode = nanoid(SHORT_CODE_MAX_LENGTH);
                this.retryNum = this.retryNum - 1;
                return this.insert();
            }

            console.error(e);
            throw new ShortUrlError('存储短域名信息失败！');
        }
        return this.data.shortCode;
    }
}

/**
 * 根据参数创建短域名
 * @param param 创建短域名的参数
 * @returns 创建短域名后的返回值
 */
async function createShortUrl(param: IShortUrlParam): Promise<IShortUrlResult> {
    // 校验-参数较少，直接校验
    if (!isWebUri(param.longUrl)) {
        throw new ShortUrlError('长域名格式不正确！');
    }
    if (param.shortCode && param.shortCode.length > SHORT_CODE_MAX_LENGTH) {
        throw new ShortUrlError('短码过长！');
    }
    // 指定短码是否存在
    if (param.shortCode && param.shortCode.length > 0) {
        // 缓存中存在直接抛出存在异常
        if (cache.get(param.shortCode)) {
            throw new ShortUrlError('短码已存在！');
        }
        // 继续去数据库查询判断是否存在
        const result = await new ShortUrl({
            shortCode: param.shortCode,
            longUrl: param.longUrl,
        }).findByShortCode();
        if (result) {
            throw new ShortUrlError('短码已存在！');
        }
    }
    const shortCode = await new ShortUrl({
        shortCode: param.shortCode ?? nanoid(SHORT_CODE_MAX_LENGTH),
        longUrl: param.longUrl,
    } as IShortUrl).insert();

    return {
        shortUrl: `${SHORT_URL_PREFIX}${shortCode}`,
        code: StatusCode.Success,
    } as IShortUrlResult;
}

/**
 * 通过短码获取对应的长域名
 * @param shortCode 唯一短码
 * @returns 包含长链接的数据
 */
async function readShortUrl(shortCode: string): Promise<IReadShortUrlResult> {
    if (!shortCode || shortCode.length === 0) {
        throw new ShortUrlError('短码不能为空！');
    }
    if (shortCode && shortCode.length > SHORT_CODE_MAX_LENGTH) {
        throw new ShortUrlError('短码过长！');
    }

    // 先从缓存中获取
    let longUrl = cache.get(shortCode);
    if (!longUrl) {
        const result = await new ShortUrl({
            shortCode: shortCode,
        } as IShortUrl).findByShortCode();
        if (!result) {
            throw new ShortUrlError('未找到对应的长域名！');
        }
        longUrl = result.longUrl;
        // 设置到缓存
        cache.set(shortCode, longUrl);
    }

    return {
        code: StatusCode.Success,
        longUrl,
    } as IReadShortUrlResult;
}
export {
    SHORT_CODE_MAX_LENGTH,
    SHORT_URL_PREFIX,
    StatusCode,
    IResult,
    IShortUrlParam,
    IShortUrlResult,
    IReadShortUrlResult,
    createShortUrl,
    readShortUrl,
    IShortUrl,
    ShortUrl,
};
