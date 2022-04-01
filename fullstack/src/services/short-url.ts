import { redis } from '../libs/redis-connection';
import { shortUrlModel } from './models';

export async function getUrl(id: number) {
  return await shortUrlModel.findByPk(id);
}

export async function saveUrl(longUrl: string) {
  try {
    const record = await shortUrlModel.create({
      longUrl,
    });
    return record;
  } catch (err) {
    console.log(err);
  }
}

export async function saveToRedis(key: string, val: string) {
  try {
    await redis.set(key, val);
  } catch (err) {
    console.log(err);
  }
}
