import 'reflect-metadata';
import app from '../../src/server';

export default async function setup() {
  await app.listen();
  const redisClient:any = app.redisService.getClient();
  await redisClient.flushAll('SYNC');
}
