import express, { Express, Request, Response } from 'express';
import cors from 'cors'
import { createService } from './service';
import { createMemoryRepository } from './repository/repository_memory';
import { initRedisConnnection } from './db/redis';
import { createRedisRepository } from './repository/repository_redis';
import { createSqlRepository } from './repository/repository_sql';
import { initSqlConnection } from './db/sqlite';

export default async function(dbType: 'memory' | 'sqlite' | 'redis' = 'memory') {
  const app: Express = express()

  app.use(cors())

  let repoFactoryMap: { [key:string]: () => Promise<UrlRepository> }  = {
    'memory': createMemoryRepository,
    'sqlite': async () => createSqlRepository(await initSqlConnection()),
    'redis': async () => createRedisRepository(await initRedisConnnection(true))
  }
  
  const repo = await repoFactoryMap[dbType]()
  const service = createService(repo)

  app.get('/long2short', async (req: Request, res: Response) => {
    const url = req.query.url as string

    let urlData = await service.createOrQueryShort(url)

    res.send({
      success: true,
      message: 'ok',
      data: { short: urlData.short }
    })
  });

  app.get('/short2long', async (req: Request, res: Response) => {
    const short = req.query.short as string
    const urlData = await service.queryByShort(short)
    if (!urlData) {
      return res.send({ success: false, message: 'not found' })
    }
    res.send({
      success: true,
      message: 'ok',
      data: { url: urlData.url }
    })
  })

  return app
}