import dotenv from 'dotenv';

import prisma from '@/prisma/client';
import { createServer } from '@/utils/server';
import { Context } from '@/utils/context';

dotenv.config();

const port = process.env.PORT || 3000;
const context: Context = {
  prisma: prisma,
};

createServer(context)
  .then((server) => {
    server.listen(port, () => {
      console.log(`⚡️[server]: Server is running at https://localhost:${port}`);
    });
  })
  .catch((err) => {
    console.error(`Error: ${err}`);
  });
