import dotenv from 'dotenv';

import { PrismaClient } from '@prisma/client';

import { createServer } from '@/utils/server';
import { Context } from '@/utils/context';
import { shortUrlsRouter } from '@/short-urls/short-urls.router';

dotenv.config();

const port = process.env.PORT || 3000;
const context: Context = {
  prisma: new PrismaClient(),
};

createServer(context)
  .then((server) => {
    server.use('/api/short-urls', shortUrlsRouter);
    server.use('/', shortUrlsRouter);

    server.listen(port, () => {
      console.log(`⚡️[server]: Server is running at http://localhost:${port}`);
    });
  })
  .catch((err) => {
    console.error(`Error: ${err}`);
  });
