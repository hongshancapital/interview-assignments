import { stringToHash, checkUrl } from '../common/utils';
import domain from '../common/domain';
import { insertShortDomain, getLongDomain } from '../db';
import { URL } from 'url';

/**
 * 根据长链接生成短连接
 * @param urlParam 传入的url链接
 * @returns json数据
 */
export const generateShortUrl = async (urlParam: string) => {
    try {
        if (!checkUrl(urlParam)) {
            throw 'input is not a url'
        }
        const key = stringToHash(urlParam);
        // 查询是否数据库已包含该短连接hash值 ，key为8位hash 如78f38590
        const longUrl = await getLongDomain(key);
        // console.log('hasValue', longUrl);
        // 如果没有，则插入该hash到长链接的映射
        if (!longUrl) {
            // 
            const isSuccess = await insertShortDomain(key, urlParam);
            // console.log('key', key);
            // console.log('insert isSuccess', isSuccess);
        }
        // 如果库里有重复的，贼直接返回短连接
        return {
            code: 0, // 结果正常
            data: {
                url: domain.concat(key)
            }
        };
    } catch (e) {
        console.log('create data err', e);
        // 遇到异常，直接返回code和错误信息
        return { code: -1, msg: e }
    }
}

/**
 * 根据短连接查询长链接
 * @param urlParam 传入的url链接
 * @returns json数据
 */
export const generateLongUrl = async (urlParam: string) => {
    try {
        // 将url字符串转为URL对象
        if (!checkUrl(urlParam)) {
            throw new Error('输入的不是一个链接')
        }
        let url = new URL(urlParam);
        // 取path里面不包含?参数的部分，也就是hash 8位标识
        const key = url.pathname.slice(1).split('?')[0];
        const longUrl = await getLongDomain(key); // 根据hash标识查询长链接
        if (!longUrl) {
            return {
                code: -1, // 结果正常
                data: {
                    url: 'not found'
                }
            };
        }
        return {
            code: 0, // 结果正常
            data: {
                url: longUrl
            }
        };
    } catch (e) {
        // 遇到异常，直接返回code和错误信息
        return { code: -1, msg: JSON.stringify(e) }
    }
}   