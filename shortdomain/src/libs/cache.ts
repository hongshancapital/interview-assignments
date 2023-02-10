import client from './rdclient'
import Url from '../libs/url'

interface CacheResult {
    // bloom 过滤器检测结果是否可能存在（如果存在，有可能在缓存中，有可能在数据库中）
    maybeExists: boolean;
    // 缓存中的值
    cacheVal: string;
}

let flagKeyPrefix = 'short:flag:'
let ttl = 86400 * 7

let BL_KEY_URL = 'short:bl:url'
let BL_KEY_FLAG = 'short:bl:flag'

/**
 * 检查 item 是否在 bloom 过滤器中存在
 * 
 * @param key - bloom 过滤器的 key
 * @param item - 需要检查的值
 * @returns 
 */
async function checkBloomExists(key: string, item: string | number): Promise<boolean> {
    return Boolean(await client.call('BF.EXISTS', key, item))
}

async function isFlagInBloom(flag: string): Promise<boolean> {
    return checkBloomExists(BL_KEY_FLAG, flag)
}

async function isUrlInBloom(url: Url): Promise<boolean> {
    return checkBloomExists(BL_KEY_URL, url.crc32)
}

/**
 * 将短链接和长链接映射关系添加到缓存中
 * 同时为短链接 flag 和长链接的 crc32 值添加到 bloom 过滤器中
 * 
 * @param flag - 短链接的 flag
 * @param url - 长链接 url
 * @param ttl - 过期时间（秒）
 */
async function add(flag: string, url: Url) {
    // 使用 lua 脚本执行多条命令保证原子性，同时提升性能
    const script = `
        -- 设置缓存
        redis.call('SET', KEYS[1], ARGV[1], 'EX', ARGV[4]);
        -- 将 flag 加入到 bloom 过滤器
        redis.call('BF.ADD', '${BL_KEY_FLAG}', ARGV[2]);
        -- 将 url 的 crc32 值加入到 bloom 过滤器
        redis.call('BF.ADD', '${BL_KEY_URL}', ARGV[3]);

        return 1;
    `

    return await client.eval(script, 1, flagKeyPrefix + flag, url.url, flag, url.crc32, ttl)
}

/**
 * 从缓存中检索 flag 对应的长链接 url
 * 每次命中缓存时延长缓存有效期
 * 当缓存不存在时会检查 bloom 过滤器以判断数据库中是否可能存在
 * 
 * @param flag - 短链接 flag
 * @returns
 */
async function get(flag: string): Promise<CacheResult> {
    const script = `
        local val = redis.call('GET', KEYS[1]);
        if (val) then
            -- 存在值，延长其有效期
            redis.call('EXPIRE', KEYS[1], ARGV[2]);
        else
            -- 不存在，检查 bloom 过滤器
            val = redis.call('BF.EXISTS', ARGV[3], ARGV[1]);
        end

        return val;
    `

    const rst = await client.eval(script, 1, flagKeyPrefix + flag, flag, ttl, BL_KEY_FLAG)

    if (rst == 0 || rst == 1) {
        // 返回的是 bloom 过滤器的值，说明缓存不存在
        return { maybeExists: Boolean(rst), cacheVal: '' }
    }

    return { maybeExists: true, cacheVal: rst as string }
}

/**
 * 提供函数共集成测试用，防止集成测试对相关环境产生额外影响
 * 
 * @param arg 
 */
/* istanbul ignore next */
export function integrationTestInit(arg: { prefix?: string, ttl?: number, blKeyUrl?: string, blKeyFlag?: string } = {}) {
    flagKeyPrefix = arg?.prefix || 'short:flag:inte:'
    ttl = arg?.ttl || 1800
    BL_KEY_URL = arg?.blKeyUrl || 'short:bl:url:inte'
    BL_KEY_FLAG = arg?.blKeyFlag || 'short:bl:flag:inte'

    return { flagKeyPrefix, ttl, BL_KEY_URL, BL_KEY_FLAG }
}

export { CacheResult }
export default { isFlagInBloom, isUrlInBloom, get, add }