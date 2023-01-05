import express from 'express';
import { Express, Response } from 'express-serve-static-core';
import helmet from 'helmet';
import dotenv from 'dotenv';
import { Context, setContext } from './context';
import prisma from '../prisma/client';

export async function createServer(): Promise<Express> {
  dotenv.config();

  const server: Express = express();

  server.use(helmet());
  server.use(express.json());
  server.use(express.urlencoded({ extended: true }));
  server.use((_, res, next) => {
    const context : Context = {
      prisma: prisma
    }
    setContext(res, context)
    next()
  })

  server.get('/', (_, res: Response) => {
    res.status(200).json({
      message: 'Ok',
    });
  });
  return server;
}
