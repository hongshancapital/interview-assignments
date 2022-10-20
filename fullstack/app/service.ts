
export const getOriginUrl = async (shortUrl: string) => {

  // step.1 查询数据库是否已存在shortUrl

  // step.2-1 存在 - 返回长链

  // step.2-2 不存在，报错

  return Promise.resolve(shortUrl);
}

export const getShortUrl = async (originUrl: string) => {

  // step.1 查询长链是否存在

  // step.2-1 存在 - 返回短链

  // step.2-2 不存在，生成新的短链

  // step.3 hash

  // step.4 62进制转义

  // step.5 唯一索引插入检测

  // step.6-1 插入成功，返回短链

  // step.6-2 插入失败，短链撞hash，回到step3

  return Promise.resolve(originUrl);
}