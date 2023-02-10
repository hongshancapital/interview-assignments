import { InvalidFlagError, InvalidNumberError } from "../errors"
import math from './math'

const CURR_VERTION = 1// 当前算法版本号
const MAX_LEN = 8// flag 最大字符数
const MAX_ID = 3521614606207// zzzzzzz 的十进制表示
const MAX_VERSION = 3// 0011
const MOD_BASE = 14// 1101 加 1

/**
 * 计算校验和
 * @param id 
 * @returns 
 */
function checksum(id: number): number {
    return id % MOD_BASE
}

/**
 * 生成标志字符
 * 版本号放在高 4 位，校验和放在低 4 位
 * 为了能用 1 字节的 62 进制表示，标志位的十进制值最大是 61（二进制 00111101）
 * 
 * @param checksum 
 * @param version 
 * @returns 标志位的 62 进制字符
 */
function mark(id: number, version: number): string {
    return math.base10to62(((version - 1) << 4) | checksum(id))
}

/**
 * 根据标志字符解析出版本号和校验和
 * 
 * @param mk - 标志字符
 * @returns [版本号, 校验和]
 */
function unmark(mk: string): [number, number] {
    const base10 = math.base62to10(mk)
    const ver = (base10 & 240) >> 4// 和 11110000 与运算后右移 4 位
    const ck =base10 & 15// 和 1111 与运算
    
    return [ver + 1, ck]
}

/**
 * 校验 flag 的合法性
 * 1. 不能超过 MAX_LEN 字符
 * 2. 必须是 0-9、a-z、A-Z
 * 3. 校验和和版本号需正确
 * 
 * @param flag
 * @throws {InvalidFlagError} 不合法抛异常
 */
function validate(flag: string) {
    if (!/^[0-9a-zA-Z]{1,8}$/.test(flag)) {
        throw new InvalidFlagError(`flag 必须是 1-${MAX_LEN} 位的数字或字母(flag:${flag})`)
    }

    const len = flag.length
    const base = flag.substring(0, len - 1)
    const mark = flag.substring(len - 1)

    const [ver, ck] = unmark(mark)

    if (ver < 1 || ver > MAX_VERSION || checksum(math.base62to10(base)) !== ck) {
        throw new InvalidFlagError(`标志位校验错误(flag:${flag})`)
    }
}

/**
 * 将 id 转成 flag
 * 当前算法：flag 最多有 8 个字符，其中最后一个字符是标志位，其它字符是十进制 id 的 62 位表示 
 * 
 * @param id 
 * @param v - 版本号
 * @todo 需根据 version 采用不同的生成算法
 * @todo 可采用可逆乱序算法打乱字符顺序，防止外部猜测
 */
function id2flag(id: number, v: number = 1): string {
    if (id > MAX_ID || id < 1) {
        throw new InvalidNumberError(`id 编号的值不能大于 ${MAX_ID} 且不能小于 1`)
    }

    if (v > MAX_VERSION || v < 1) {
        throw new InvalidNumberError(`flag 生成算法版本号不能大于 ${MAX_VERSION} 且不能小于 1`)
    }

    return math.base10to62(id) + mark(id, v)
}

/**
 * 将 flag 转成 id
 * 如果 flag 格式非法则抛异常
 * 
 * @param flag 
 */
function flag2id(flag: string): number {
    validate(flag)

    return math.base62to10(flag.substring(0, flag.length - 1))
}

export default { id2flag, flag2id, MAX_ID, MAX_VERSION, CURR_VERTION }