import validUrl = require('valid-url');
import config = require('config');
import base62 = require('base62');
import md5 = require('md5');
import { getDbConnection } from '../../libs/db.lib';
import { redisAsync } from '../../libs/redis.lib';
import { IShortUrlInfo } from './interfaces/i-short-url-info.interface';
import { ShortUrl } from './entities/short-url.entity';

const counterRedisKey = 'Counter';
const cacheDuration = config.get<number>('cacheDuration');
const maxId = base62.decode('ZZZZZZZZ');

const getShortUrlRedisKey = (key: string): string => {
  return `Info:${key}`;
};

const getShortUrlLockRedisKey = (hash: string): string => {
  return `Lock:${hash}`;
};

const validKey = (key: string): boolean => {
  return key.length <= 8 && key.length > 0;
};

const getKeyById = (id: number): string => {
  return base62.encode(id);
};

const getIdByKey = (key: string): number => {
  return base62.decode(key);
};

const getShortUrlRepository = () => {
  const connection = getDbConnection();
  return connection.getRepository(ShortUrl);
};

const createShortUrl = async (url: string): Promise<IShortUrlInfo> => {
  if (!url || typeof url !== 'string') {
    throw new Error('miss param url');
  }
  const finalUrl = url.trim();

  if (!validUrl.isWebUri(finalUrl)) {
    throw new Error('invalid url');
  }

  const hash = md5(url);
  const shortUrlRepository = getShortUrlRepository();
  const exist = await shortUrlRepository.findOne({
    select: ['id'],
    where: {
      hash,
      deletedAt: null,
    },
  });
  if (exist) {
    return {
      key: getKeyById(exist.id),
      url,
    };
  }

  const lockRedisKey = getShortUrlLockRedisKey(hash);
  const lockRes = await redisAsync.incr(lockRedisKey);
  if (lockRes > 1) {
    throw new Error('annother same create url request is processing');
  }

  const id = await redisAsync.incr(counterRedisKey);
  if (id > maxId) {
    await redisAsync.decr(counterRedisKey);
    await redisAsync.del(lockRedisKey);
    throw new Error('amount of url is out of range');
  }

  const key = getKeyById(id);

  try {
    await shortUrlRepository.save(
      shortUrlRepository.create({
        id,
        url,
        hash,
      }),
    );
  } catch (error) {
    await redisAsync.del(lockRedisKey);
    throw new Error('create url failed');
  }

  await redisAsync.setex(getShortUrlRedisKey(key), cacheDuration, url);
  await redisAsync.del(lockRedisKey);

  return {
    key,
    url,
  };
};

const getShortUrl = async (key: string): Promise<IShortUrlInfo> => {
  if (!validKey(key)) {
    throw new Error('invalid key');
  }

  const redisKey = getShortUrlRedisKey(key);
  let url = await redisAsync.get(redisKey);

  if (url === '0') {
    return null;
  }

  const id = getIdByKey(key);

  const counter = await redisAsync.get(counterRedisKey);
  if (id > counter) {
    return null;
  }

  if (!url) {
    const raw = await getShortUrlRepository().findOne({
      select: ['url'],
      where: {
        id,
        deletedAt: null,
      },
    });
    if (!raw) {
      await redisAsync.setex(redisKey, cacheDuration, '0');
      return null;
    }
    url = raw.url;
    await redisAsync.setex(redisKey, cacheDuration, url);
  }
  return {
    key,
    url,
  };
};

const initCounter = async (): Promise<void> => {
  const raw = await getShortUrlRepository().findOne({
    select: ['id'],
    order: {
      id: 'DESC',
    },
  });

  let maxId = 0;
  if (raw) {
    maxId = raw.id;
  }
  redisAsync.set(counterRedisKey, maxId);
};

export const shortUrlService = {
  createShortUrl,
  getShortUrl,
  initCounter,
};
