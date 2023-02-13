import { mockDeep } from 'jest-mock-extended';
import { PrismaClient as OriginalPrismaClient, Prisma } from '@prisma/client';
import { PrismaClientKnownRequestError } from "@prisma/client/runtime";

jest.mock('@prisma/client', () => ({
  PrismaClient: function () {
    return mockDeep<OriginalPrismaClient>();
  }
}));

import ShortLinkController from "../controller";
import dbClient from '../database';
import redisClient from "../cache"
import { Callback, Result } from 'ioredis';

test("test add with key", async () => {
    const originLink = "http://www.baidu.com0";
    const addKey = "baidu0"
    const addController:ShortLinkController = new ShortLinkController(addKey, originLink);
    const {key} = await addController.add()
    expect(key).toBe(addKey)
})

test("test add with duplicate key", async () => {
    const originLink = "http://www.baidu1.com";
    const addController:ShortLinkController = new ShortLinkController("baidu1", originLink);
    jest.spyOn(dbClient.short_link, "create").mockImplementationOnce(() => {
        throw new PrismaClientKnownRequestError('', {code: 'P2002', clientVersion: '', meta: undefined, batchRequestIdx: 0});
    });
    const {key, error} = await addController.add();
    expect(error).not.toBeNull();
})

test("test add with duplicated key and retry", async () => {
  const originLink = "http://www.baidu1.com";
  const addController:ShortLinkController = new ShortLinkController(undefined, originLink);
  jest.spyOn(dbClient.short_link, "create").mockImplementationOnce(() => {
      throw new PrismaClientKnownRequestError('', {code: 'P2002', clientVersion: '', meta: undefined, batchRequestIdx: 0});
  })
  const {key, error} = await addController.add();
  expect(key.length).toBe(ShortLinkController.KeyLength);
})

test("test add with database exception", async () => {
  const originLink = "http://www.baidu2.com";
  const addController:ShortLinkController = new ShortLinkController("baidu2", originLink);

  const err = new Error("network exception");
  jest.spyOn(dbClient.short_link, "create").mockImplementationOnce(() => {
    throw err;
  });

  try {
    const {key, error} = await addController.add();
  } catch (error) {
    expect(error).toBe(err);
  }

  const {originLink: result} = await addController.get();
  expect(result).toBeNull();
})

test("test add with redis exception", async () => {
  const addOriginLink = "http://www.baidu3.com";
  const addController:ShortLinkController = new ShortLinkController("baidu3", addOriginLink);

  const err = new Error("network exception");
  jest.spyOn(redisClient, 'set').mockImplementationOnce(() => {
    throw err;
  })

  try {
    const {key, error} = await addController.add();
  } catch (error) {
    expect(error).toBe(err);
  }

  const {originLink: result} = await addController.get();
  expect(result).toBeNull();
})

test("test add with random key", async () => {
    const addOriginLink = "http://www.baidu4.com";
    const addController:ShortLinkController = new ShortLinkController(undefined, addOriginLink);
    const {key: key1, error: err1} = await addController.add()
    expect(err1).toBeNull()
})

test("test get existed short link from redis", async () => {
  const addOriginLink = "http://www.baidu5.com";
  const addKey = "baidu5"
  const addController:ShortLinkController = new ShortLinkController(addKey, addOriginLink);
  const {key} = await addController.add()

  const {originLink: result} = await addController.get();
  expect(result).toBe(addOriginLink);
})

test("test get existed short link from database", async () => {
  const addOriginLink: string = "http://www.baidu6.com";
  const addKey = "baidu6"
  const addController:ShortLinkController = new ShortLinkController(addKey, addOriginLink);
  const short_link = {
    id: 123,
    key: addKey,
    originLink: addOriginLink,
    createdAt: new Date(),
    updatedAt: new Date(),
    deletedAt: null,
  }
  jest.spyOn(redisClient, 'get').mockResolvedValueOnce(null)

  jest.spyOn(dbClient.short_link, 'findUnique').mockResolvedValueOnce(short_link)
  
  const {originLink: result} = await addController.get();
  expect(result).toBe(addOriginLink);
})

test("test get redis with exception and database has value", async () => {
  const addOriginLink: string = "http://www.baidu7.com";
  const addKey = "baidu7"
  const addController:ShortLinkController = new ShortLinkController(addKey, addOriginLink);
  const short_link = {
    id: 123,
    key: addKey,
    originLink: addOriginLink,
    createdAt: new Date(),
    updatedAt: new Date(),
    deletedAt: null,
  }
  jest.spyOn(redisClient, 'get').mockResolvedValueOnce(null)

  jest.spyOn(dbClient.short_link, 'findUnique').mockResolvedValueOnce(short_link)
  
  const {originLink: result} = await addController.get();
  expect(result).toBe(addOriginLink);
})

test("test get from redis with exception and database has no value", async () => {
  const addKey = "baidu8"
  const addController:ShortLinkController = new ShortLinkController(addKey);

  jest.spyOn(redisClient, 'get').mockImplementationOnce(() => {
    throw new Error("network exception")
  })
  
  const {originLink: result} = await addController.get();
  expect(result).toBeNull();
})

test("test get redis not exist && database excetpion", async () => {
  const addKey = "baidu9"
  const addController:ShortLinkController = new ShortLinkController(addKey);
  jest.spyOn(redisClient, 'get').mockResolvedValueOnce(null);

  const err = new Error("network exception");
  jest.spyOn(dbClient.short_link, 'findUnique').mockImplementationOnce(() => {
    throw new Error("network exception");
  })
  
  const {originLink, error} = await addController.get();
  expect(error).not.toBe(null);
})

test("test get redis exception && database excetpion", async () => {
  const addKey = "baidu10"
  const addController:ShortLinkController = new ShortLinkController(addKey);

  const err = new Error("network exception");

  jest.spyOn(redisClient, 'get').mockImplementationOnce(() => {
    throw err;
  })

  jest.spyOn(dbClient.short_link, 'findUnique').mockImplementationOnce(() => {
    throw err;
  })
  
  try {
    const {originLink, error} = await addController.get();
  } catch (error) {
    expect(error).toBe(err);
  }
})