import 'reflect-metadata';
import appServer from '../../src/server';
import { UrlModel } from '../../src/models/url.model';
import { UserModel } from '../../src/models/user.model';

export default async function teardown() {
  const redisClient: any = appServer.redisService.getClient();
  await redisClient.flushAll('SYNC');
  await UrlModel.deleteMany({});
  await UserModel.deleteMany({});

  await appServer.getServer().close();
  await appServer.redisService.disconnect();
  await appServer.mongoService.disconnect();
  
}