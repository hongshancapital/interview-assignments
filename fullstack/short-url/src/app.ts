import dotenv from 'dotenv';
import { PrismaClient } from '@prisma/client';

import { createServer } from '@/utils/server';
import { Context } from '@/utils/context';
import { queryShortUrlOnlyRouter, shortUrlsRouter } from '@/short-urls/short-urls.router';
import type { Server } from 'http';
import { AddressInfo } from 'net';

dotenv.config();

const port = process.env.PORT || 3000;
const context: Context = {
  prisma: new PrismaClient(),
};

let connection: Server;

export const startApp = async (): Promise<AddressInfo> => {
  const server = await createServer(context, (app) => {
    app.use('/api/short-urls', shortUrlsRouter);
    app.use('/', queryShortUrlOnlyRouter);
  });

  return await new Promise((resolve) => {
    connection = server.listen(port, () => {
      console.log(`⚡️[server]: Server is running at http://localhost:${port}`);
      resolve(connection.address() as AddressInfo);
    });
  });
};

export const stopApp = () => {
  return new Promise<void>((resolve) => {
    connection.close(() => {
      console.log(`⚡️[server]: Server is stopping...`);
      resolve();
    });
  });
};
