import { redis } from './redis'
import * as shortid from 'shortid'


/** 生成未使用的id */
const generateValidId: () => Promise<string> = async () => {
    const id = shortid()
    if (await redis.get(id)) return generateValidId()
    return id
}

/** 保存url并返回id */
const saveUrlAndReturnShortId = async (url: string) => {
    const id = await generateValidId()
    await redis.set(id, url)
    return id
}

/** 根据id获取url */
const getUrlWithShortId = async (id: string) => {
    const url = await redis.get(id)
    return url
}

export {
    generateValidId,
    saveUrlAndReturnShortId,
    getUrlWithShortId
}