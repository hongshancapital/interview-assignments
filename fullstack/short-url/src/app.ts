import dotenv from 'dotenv';

import { PrismaClient } from '@prisma/client';

import { createServer } from '@/utils/server';
import { Context } from '@/utils/context';
import { shortUrlsRouter } from '@/short-urls/short-urls.router';
import type { Server } from 'http';

dotenv.config();

const port = process.env.PORT || 3000;
const context: Context = {
  prisma: new PrismaClient(),
};

let connection: Server;

export const startApp = () =>
  createServer(context)
    .then((server) => {
      server.use('/api/short-urls', shortUrlsRouter);
      server.use('/', shortUrlsRouter);

      connection = server.listen(port, () => {
        console.log(`⚡️[server]: Server is running at http://localhost:${port}`);
      });
    })
    .catch((err) => {
      console.error(`Error: ${err}`);
    });

export const stopApp = () => {
  return new Promise<void>((resolve) => {
    connection.close(() => {
      console.log(`⚡️[server]: Server is stopping...`);
      resolve();
    });
  });
};
