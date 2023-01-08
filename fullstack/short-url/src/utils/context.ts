import { PrismaClient } from '@prisma/client';
import { DeepMockProxy, mockDeep } from 'jest-mock-extended';
import { Response } from 'express-serve-static-core';

export type Context = {
  prisma: PrismaClient;
};

export const setContext = (res: Response, context: Context) => {
  res.locals.context = context;
};

export const getContext = (res: Response): Context => {
  return res.locals.context as Context;
};

export type MockContext = {
  prisma: DeepMockProxy<PrismaClient>;
};

export const createMockContext = (): MockContext => {
  return {
    prisma: mockDeep<PrismaClient>(),
  };
};
