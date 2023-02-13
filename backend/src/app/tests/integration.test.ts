import { mockDeep } from 'jest-mock-extended';
import fetch from 'node-fetch';
import ShortLinkController from '../controller';
import { PrismaClient as OriginalPrismaClient, Prisma } from '@prisma/client';


jest.mock('@prisma/client', () => ({
  PrismaClient: function () {
    return mockDeep<OriginalPrismaClient>();
  }
}));

const host = `http://app_test_server:${process.env.PORT || 3000}`;

const addFunction = async (originLink: string, key?: string) => {
  const response = await fetch(`${host}/add`, {
    method: 'POST',
    body: JSON.stringify({
      key,
      originLink, 
    }),
    headers: {
      'Content-Type': 'application/json'
    }
  })
  return response.json();
}

test("add with key", async () => {
  const originLink = "https://www.baidu.com";
  const key = ShortLinkController.randomKey()
  const data = await addFunction(originLink, key);
  expect(data?.key).toBe(key);
  expect(data?.error).toBe(null);

  const data2 = await addFunction(originLink, key);
  expect(data2?.key).toBe(key);
  expect(data2?.error).toBe('key has existed!');
})


test("add without key", async () => {
  const originLink = "https://www.baidu.com";
  const data = await addFunction(originLink);
  expect(data?.key.length).toBe(ShortLinkController.KeyLength);
  expect(data?.error).toBe(null);

  const response = await fetch(`${host}/${data?.key || ''}`)
  expect(response.status).toBe(200);
})


test("get without key", async () => {
  const response = await fetch(`${host}/abcde`)
  const data = await response.json()
  expect(data?.error).toBeNull();
  expect(data?.originLink).toBeNull();
})